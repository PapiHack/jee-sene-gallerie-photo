package sn.sgp.servlets;

import java.io.IOException;
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
import sn.sgp.managers.AlbumManager;
import sn.sgp.managers.ImageManager;
import sn.sgp.utils.CookieManager;
import sn.sgp.utils.UploadImage;
import sn.sgp.validators.ImageValidator;

/**
 * @author papihack
 * @version 0.0.1
 * 
 *          Servlet permettant de gérer la gestion des images.
 */
@WebServlet(
{ "/user/album/images", "/user/album/image/add", "/user/album/image/update", "/user/album/image/delete" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 50, maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024
		* 500, location = "/uploads")
public class ImageController extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String ADD_IMAGE_PAGE = "/WEB-INF/pages/AddImage.jsp";
	private static final String LIST_IMAGE_PAGE = "/WEB-INF/pages/ImageList.jsp";
	private static final String USER_ALBUM_URL = "/user/albums";
	
	private CookieManager cookieManager;
	@EJB
	private AlbumManager albumManager;
	@EJB
	private ImageManager imageManager;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String path = request.getServletPath();
		request.setAttribute("path", path);
		this.cookieManager = new CookieManager(request, response);
		Album album;
		String albumId;
		switch (path)
		{
			case "/user/album/image/add":
				request.setAttribute("update", "add");
				albumId = request.getParameter("album");
				if (albumId == null || albumId.isEmpty())
				{
					response.sendRedirect(request.getContextPath() + USER_ALBUM_URL);
				}
				album = this.albumManager.findById(Long.parseLong(albumId));
				request.setAttribute("albumTitle", album.getTitre());
				request.setAttribute("albumId", albumId);
				request.getServletContext().getRequestDispatcher(ADD_IMAGE_PAGE).forward(request, response);
				break;
			case "/user/album/image/update":
				request.setAttribute("update", "update");
				break;
			case "/user/album/image/delete":
				break;
			case "/user/album/images":
				albumId = request.getParameter("album");
				if (albumId == null || albumId.isEmpty())
				{
					response.sendRedirect(request.getContextPath() + USER_ALBUM_URL);
				}
				album = this.albumManager.findById(Long.parseLong(albumId));
				request.setAttribute("album", album);
				request.getServletContext().getRequestDispatcher(LIST_IMAGE_PAGE).forward(request, response);
				break;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String path = request.getServletPath();
		request.setAttribute("path", path);
		this.cookieManager = new CookieManager(request, response);
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		ImageValidator imageValidator = new ImageValidator(request);
		UploadImage uploader = new UploadImage(request);
		HashMap<String, String> result = new HashMap<String, String>();
		HashMap<String, String> form = new HashMap<String, String>();
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		JsonObject errors = null;
		JsonObject data = null;
		String description;
		String titre;
		String hauteur;
		String largeur;
		switch (path)
		{
			case "/user/album/image/add":
				request.setAttribute("update", "add");
				Long albumId = Long.parseLong(uploader.getValeur(request.getPart("albumId")));
				Album album = this.albumManager.findById(albumId);
				description = uploader.getValeur(request.getPart("description"));
				titre = uploader.getValeur(request.getPart("titre"));
				hauteur = uploader.getValeur(request.getPart("hauteur"));
				largeur = uploader.getValeur(request.getPart("largeur"));
				result = imageValidator.validate();
				if (result.isEmpty())
				{
					List<Image> images = uploader.saveImages();
					if (!images.isEmpty())
					{
						Image img = images.get(0);
						img.setTitre(titre);
						img.setDescription(description);
						img.setHauteur(hauteur);
						img.setLargeur(largeur);
						img.setAlbum(album);
						this.imageManager.add(img);
						this.cookieManager = new CookieManager(request, response);
						this.cookieManager.createCookie("addImage", "Ajout de l'image : OK !", 10);
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
					form.put("hauteur", hauteur);
					form.put("largeur", largeur);
					jsonBuilder.add("titre", titre);
					jsonBuilder.add("description", description);
					jsonBuilder.add("hauteur", hauteur);
					jsonBuilder.add("largeur", largeur);
					data = jsonBuilder.build();
					for (String key : result.keySet())
					{
						jsonBuilder.add(key, result.get(key));
					}
					errors = jsonBuilder.build();
					response.getWriter().print(jsonBuilder.add("data", data).add("errors", errors).build());
				}
				break;
			case "/user/album/image/update":
				request.setAttribute("update", "update");
				break;
		}
		
	}
	
}
