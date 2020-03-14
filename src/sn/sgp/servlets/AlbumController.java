package sn.sgp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sn.sgp.beans.Album;
import sn.sgp.beans.Image;
import sn.sgp.beans.User;
import sn.sgp.managers.AlbumManager;
import sn.sgp.managers.UserManager;
import sn.sgp.utils.CookieManager;
import sn.sgp.utils.EnumAlbumStatus;
import sn.sgp.utils.UploadImage;
import sn.sgp.validators.AlbumValidator;

/**
 * Servlet implementation class AlbumController
 */
@SuppressWarnings("serial")
@WebServlet(
{ "/user/albums", "/user/album/add", "/user/album/update", "/user/album/delete", "/user/gallery" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 50, maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024
		* 500, location = "/uploads")
public class AlbumController extends HttpServlet
{
	
	private static final String LIST_ALBUM_PAGE = "/WEB-INF/pages/UserAlbums.jsp";
	private static final String ADD_ALBUM_PAGE = "/WEB-INF/pages/AddAlbum.jsp";
	private static final String GALLERY_PAGE = "/WEB-INF/pages/Gallery.jsp";
	private static final String USER_ALBUM_URL = "/user/albums";
	
	private CookieManager cookieManager;
	@EJB
	private AlbumManager albumManager;
	@EJB
	private UserManager userManager;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AlbumController()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String path = request.getServletPath();
		request.setAttribute("path", path);
		this.cookieManager = new CookieManager(request, response);
		User user = ((User) request.getSession().getAttribute("user")).getCompte().getUser();
		switch (path)
		{
			case "/user/albums":
				request.setAttribute("albums", this.albumManager.findUserAlbum(user));
				request.setAttribute("deleteAlbum", this.cookieManager.getCookieValue("deleteAlbum"));
				request.getServletContext().getRequestDispatcher(LIST_ALBUM_PAGE).forward(request, response);
				break;
			case "/user/gallery":
				// recupérer la liste des albums publics (et privés dont il a accés) et la
				// transmettre à la vue !
				List<Album> albums = this.albumManager.findAlbumByStatus(EnumAlbumStatus.publik);
				System.out
						.println("SiZE albumAccessible ======== " + this.albumManager.getAccessibleAlbums(user).size());
				albums.addAll(this.albumManager.getAccessibleAlbums(user));
				request.setAttribute("albums", albums);
				request.getServletContext().getRequestDispatcher(GALLERY_PAGE).forward(request, response);
				break;
			case "/user/album/add":
				request.setAttribute("update", "add");
				List<User> users = this.userManager.findUsersWithout(user.getEmail());
				request.setAttribute("users", users);
				request.getServletContext().getRequestDispatcher(ADD_ALBUM_PAGE).forward(request, response);
				break;
			case "/user/album/delete":
				String albumId = request.getParameter("album");
				if (albumId == null || albumId.isEmpty())
				{
					response.sendRedirect(request.getContextPath() + USER_ALBUM_URL);
				}
				else
				{
					this.albumManager.deleteById(Long.parseLong(albumId), request);
					this.cookieManager.createCookie("deleteAlbum", "Suppression de l'album: OK !", 5);
					response.sendRedirect(request.getContextPath() + USER_ALBUM_URL);
				}
				break;
			
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String path = request.getServletPath();
		request.setAttribute("path", path);
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		AlbumValidator albumValidator;
		UploadImage uploader = new UploadImage(request);
		HashMap<String, String> result = new HashMap<String, String>();
		HashMap<String, String> form = new HashMap<String, String>();
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		JsonObject errors = null;
		JsonObject data = null;
		String description;
		String titre;
		String statut;
		String usersSharedWith;
		switch (path)
		{
			case "/user/album/add":
				request.setAttribute("update", "add");
				response.setContentType("application/json");
				albumValidator = new AlbumValidator(request);
				description = uploader.getValeur(request.getPart("description"));
				titre = uploader.getValeur(request.getPart("titre"));
				statut = uploader.getValeur(request.getPart("statut"));
				usersSharedWith = request.getPart("users") == null ? null
						: uploader.getValeur(request.getPart("users"));
				result = albumValidator.validate();
				if (result.isEmpty())
				{
					// rediriger vers la gallerie (mes albums)
					List<Image> images = uploader.saveImages();
					List<User> usersAuthorize = new ArrayList<User>();
					if (usersSharedWith != null && !usersSharedWith.isEmpty())
					{
						String[] usersId = usersSharedWith.split(",");
						for (String userId : usersId)
						{
							usersAuthorize.add(this.userManager.findUserById(Long.parseLong(userId)));
						}
					}
					if (!images.isEmpty())
					{
						EnumAlbumStatus albumStatus = "public".equals(statut) ? EnumAlbumStatus.publik
								: EnumAlbumStatus.privé;
						Album album = new Album(titre, description, albumStatus);
						ArrayList<Image> pics = new ArrayList<Image>();
						for (Image img : images)
						{
							img.setAlbum(album);
							pics.add(img);
						}
						album.setImages(pics);
						if (album.getStatut().equals(EnumAlbumStatus.privé))
						{
							usersAuthorize
									.add(((User) request.getSession().getAttribute("user")).getCompte().getUser());
							album.setSharedWith(usersAuthorize);
						}
						album.setProprio((User) request.getSession().getAttribute("user"));
						this.albumManager.add(album);
						this.cookieManager = new CookieManager(request, response);
						this.cookieManager.createCookie("addAlbum", "Ajout de l'album : OK !", 15);
						response.getWriter().print(jsonBuilder.add("message", "OK").build());
					}
					else
					{
						response.getWriter().print(jsonBuilder
								.add("message", "Une erreur est survenue lors de l'upload des images!").build());
					}
				}
				else
				{
					form.put("titre", titre);
					form.put("description", description);
					form.put("statut", statut);
					jsonBuilder.add("titre", titre);
					jsonBuilder.add("description", description);
					jsonBuilder.add("statut", statut);
					data = jsonBuilder.build();
					// jsonBuilder.add("data", data);
					// response.getWriter().print(jsonBuilder.build());
					for (String key : result.keySet())
					{
						jsonBuilder.add(key, result.get(key));
					}
					errors = jsonBuilder.build();
					// jsonBuilder.add("errors", errors);
					response.getWriter().print(jsonBuilder.add("data", data).add("errors", errors).build());
					// request.getServletContext().getRequestDispatcher(ADD_ALBUM_PAGE).forward(request,
					// response);
				}
				break;
		}
	}
	
}
