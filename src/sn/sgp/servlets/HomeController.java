package sn.sgp.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sn.sgp.beans.Album;
import sn.sgp.beans.Compte;
import sn.sgp.beans.User;
import sn.sgp.managers.AlbumManager;
import sn.sgp.managers.CompteManager;
import sn.sgp.managers.UserManager;
import sn.sgp.utils.CookieManager;
import sn.sgp.utils.EnumAlbumStatus;
import sn.sgp.utils.EnumUserType;
import sn.sgp.validators.LoginValidator;
import sn.sgp.validators.RegisterValidator;

/**
 * @author papihack
 * @version 0.0.1
 * 
 *          Servlet permettant de gérer les fonctionnalités ne nécessitant pas
 *          une authentification.
 */
@SuppressWarnings("serial")
@WebServlet(
{ "/home", "/login", "/register", "/gallery", "/about", "/gallery/images" })
public class HomeController extends HttpServlet
{
	
	@EJB
	private UserManager userManager;
	@EJB
	private CompteManager compteManager;
	@EJB
	private AlbumManager albumManager;
	
	private CookieManager cookieManager;
	
	private static final String HOME_PAGE = "/WEB-INF/pages/Home.jsp";
	private static final String LOGIN_PAGE = "/WEB-INF/pages/Login.jsp";
	private static final String REGISTER_PAGE = "/WEB-INF/pages/Register.jsp";
	private static final String ABOUT_PAGE = "/WEB-INF/pages/About.jsp";
	private static final String GALLERY_PAGE = "/WEB-INF/pages/Gallery.jsp";
	private static final String LIST_IMAGE_PAGE = "/WEB-INF/pages/ImageList.jsp";
	private static final String GALLERY_URL = "/gallery";
	
	private static final String REDIRECT_URL_AFTER_LOGIN = "/user/home";
	private static final String REDIRECT_URL_AFTER_REGISTER = "/login";
	
	private String path;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.path = request.getServletPath();
		request.setAttribute("path", path);
		switch (this.path)
		{
			case "/home":
				this.getServletContext().getRequestDispatcher(HOME_PAGE).forward(request, response);
				// System.out.println("EMAIL => " +
				// this.userManager.isEmailExist("admin@test.com"));
				break;
			case "/login":
				this.cookieManager = new CookieManager(request, response);
				request.setAttribute("register", this.cookieManager.getCookieValue("register"));
				this.getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(request, response);
				break;
			case "/register":
				this.getServletContext().getRequestDispatcher(REGISTER_PAGE).forward(request, response);
				break;
			case "/about":
				this.getServletContext().getRequestDispatcher(ABOUT_PAGE).forward(request, response);
				break;
			case "/gallery":
				// récupérer les albums publics et les transmettre à la vue !
				request.setAttribute("albums", this.albumManager.findAlbumByStatus(EnumAlbumStatus.publik));
				this.getServletContext().getRequestDispatcher(GALLERY_PAGE).forward(request, response);
				break;
			case "/gallery/images":
				String albumId = request.getParameter("album");
				if (albumId == null || albumId.isEmpty())
				{
					response.sendRedirect(request.getContextPath() + GALLERY_URL);
				}
				Album album = this.albumManager.findById(Long.parseLong(albumId));
				request.setAttribute("album", album);
				request.getServletContext().getRequestDispatcher(LIST_IMAGE_PAGE).forward(request, response);
				break;
			default:
				this.getServletContext().getRequestDispatcher(HOME_PAGE).forward(request, response);
				break;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.path = request.getServletPath();
		request.setAttribute("path", path);
		request.setCharacterEncoding("utf-8");
		User user = null;
		switch (this.path)
		{
			case "/register":
				String nom = request.getParameter("nom");
				String prenom = request.getParameter("prenom");
				String email = request.getParameter("email");
				String login = request.getParameter("login");
				String password = request.getParameter("password");
				String confirm_password = request.getParameter("confirm_password");
				RegisterValidator registerValidator = new RegisterValidator(request, this.userManager,
						this.compteManager);
				HashMap<String, String> result = registerValidator.validate();
				// System.out.println("RESULT ===> " + result);
				if (result.isEmpty())
				{
					user = new User(nom, prenom, email, EnumUserType.simple, new Compte(login, password));
					this.userManager.add(user);
					this.cookieManager = new CookieManager(request, response);
					this.cookieManager.createCookie("register", "Inscription : OK, veuillez vous authentifier !", 5);
					response.sendRedirect(request.getContextPath() + REDIRECT_URL_AFTER_REGISTER);
				}
				else
				{
					HashMap<String, String> form = new HashMap<String, String>();
					form.put("nom", nom);
					form.put("prenom", prenom);
					form.put("email", email);
					form.put("login", login);
					form.put("mdp", password);
					form.put("cmdp", confirm_password);
					request.setAttribute("errors", result);
					request.setAttribute("form", form);
					request.getServletContext().getRequestDispatcher(REGISTER_PAGE).forward(request, response);
				}
				break;
			case "/login":
				String username = request.getParameter("login");
				String pwd = request.getParameter("password");
				LoginValidator loginValidator = new LoginValidator(request, this.compteManager);
				if (loginValidator.validate())
				{
					user = this.compteManager.findUserByLogin(username).getUser();
					// System.out.println("USER ========== " + user);
					System.out.println("User with email address " + user.getEmail() + " and " + user.getStatut()
							+ " status connected !");
					request.getSession().setAttribute("user", user);
					this.cookieManager = new CookieManager(request, response);
					this.cookieManager.createCookie("welcome",
							"Bienvenue " + user.getPrenom() + " " + user.getNom() + " !", 5);
					response.sendRedirect(request.getContextPath() + REDIRECT_URL_AFTER_LOGIN);
				}
				else
				{
					request.setAttribute("login", username);
					request.setAttribute("password", pwd);
					request.setAttribute("error", "Login ou mot de passe incorrecte !");
					request.getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(request, response);
				}
				break;
		}
	}
	
}
