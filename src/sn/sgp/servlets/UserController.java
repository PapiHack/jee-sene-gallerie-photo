package sn.sgp.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sn.sgp.beans.Compte;
import sn.sgp.beans.User;
import sn.sgp.managers.CompteManager;
import sn.sgp.managers.UserManager;
import sn.sgp.utils.CookieManager;
import sn.sgp.utils.EnumUserType;
import sn.sgp.validators.ParametreValidator;
import sn.sgp.validators.RegisterValidator;

/**
 * @author papihack
 * @version 0.0.1
 * 
 *          Servlet permettant de gérer la gestion des utilisateurs.
 */
@SuppressWarnings("serial")
@WebServlet(
{ "/user/home", "/user/list", "/user/list-admin", "/user/add", "/user/update", "/user/delete", "/user/parametre",
		"/user/my-profile" })
public class UserController extends HttpServlet
{
	
	private String path;
	
	private static final String HOME_PAGE = "/WEB-INF/pages/Home.jsp";
	private static final String USER_LIST_PAGE = "/WEB-INF/pages/UserList.jsp";
	private static final String USER_ADD_PAGE = "/WEB-INF/pages/AddUser.jsp";
	private static final String USER_PROFILE_PAGE = "/WEB-INF/pages/Profile.jsp";
	private static final String USER_PARAMETER_PAGE = "/WEB-INF/pages/Parametres.jsp";
	private static String USER_LIST_URL = "/user/list";
	
	@EJB
	private UserManager userManager;
	@EJB
	private CompteManager compteManager;
	
	private CookieManager cookieManager;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.path = request.getServletPath();
		request.setAttribute("path", path);
		Long userId;
		User user;
		HashMap<String, String> form = new HashMap<String, String>();
		this.cookieManager = new CookieManager(request, response);
		switch (this.path)
		{
			case "/user/home":
				request.setAttribute("path", "/home");
				request.setAttribute("welcome", cookieManager.getCookieValue("welcome"));
				this.getServletContext().getRequestDispatcher(HOME_PAGE).forward(request, response);
				break;
			case "/user/list":
				request.setAttribute("userDelete", cookieManager.getCookieValue("delete"));
				request.setAttribute("userAdd", cookieManager.getCookieValue("userAdd"));
				request.setAttribute("userUpdate", cookieManager.getCookieValue("userUpdate"));
				request.setAttribute("users", this.userManager.findUsersWithStatus(EnumUserType.simple));
				request.getServletContext().getRequestDispatcher(USER_LIST_PAGE).forward(request, response);
				break;
			case "/user/list-admin":
				request.setAttribute("userDelete", cookieManager.getCookieValue("delete"));
				request.setAttribute("userAdd", cookieManager.getCookieValue("userAdd"));
				request.setAttribute("userUpdate", cookieManager.getCookieValue("userUpdate"));
				request.setAttribute("users", this.userManager.findUsersWithStatus(EnumUserType.admin));
				request.getServletContext().getRequestDispatcher(USER_LIST_PAGE).forward(request, response);
				break;
			case "/user/add":
				request.setAttribute("update", "add");
				request.getServletContext().getRequestDispatcher(USER_ADD_PAGE).forward(request, response);
				break;
			case "/user/update":
				request.setAttribute("update", "update");
				userId = Long.parseLong(request.getParameter("user"));
				user = this.userManager.findUserById(userId);
				if (user == null)
				{
					response.sendRedirect(request.getContextPath() + USER_LIST_URL);
				}
				else
				{
					form.put("userId", user.getId().toString());
					form.put("nom", user.getNom());
					form.put("prenom", user.getPrenom());
					form.put("email", user.getEmail());
					form.put("statut", user.getStatut().toString());
					form.put("login", user.getCompte().getLogin());
					form.put("mdp", user.getCompte().getPassword());
					form.put("cmdp", user.getCompte().getPassword());
					request.setAttribute("form", form);
				}
				request.getServletContext().getRequestDispatcher(USER_ADD_PAGE).forward(request, response);
				break;
			case "/user/delete":
				if (request.getParameter("user") == null || request.getParameter("user").isEmpty())
				{
					response.sendRedirect(request.getContextPath() + USER_LIST_URL);
				}
				else
				{
					userId = Long.parseLong(request.getParameter("user"));
					System.out.println("UserID ===> " + userId);
					this.userManager.deleteById(userId);
					this.cookieManager = new CookieManager(request, response);
					this.cookieManager.createCookie("delete", "Suppression de l'utilisateur : OK !", 5);
					response.sendRedirect(request.getContextPath() + USER_LIST_URL);
				}
				break;
			case "/user/my-profile":
				request.getServletContext().getRequestDispatcher(USER_PROFILE_PAGE).forward(request, response);
				// Afficher tout un tas d'infos (I back on this later)
				break;
			case "/user/parametre":
				user = (User) request.getSession().getAttribute("user");
				form.put("login", user.getCompte().getLogin());
				request.setAttribute("form", form);
				request.setAttribute("edit_params", this.cookieManager.getCookieValue("edit_params"));
				request.getServletContext().getRequestDispatcher(USER_PARAMETER_PAGE).forward(request, response);
				break;
			
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.path = request.getServletPath();
		request.setAttribute("path", path);
		HashMap<String, String> form = new HashMap<String, String>();
		request.setCharacterEncoding("utf-8");
		this.cookieManager = new CookieManager(request, response);
		RegisterValidator registerValidator;
		HashMap<String, String> result;
		User user;
		String nom;
		String prenom;
		String email;
		String login;
		String statut;
		String password;
		String confirm_password;
		switch (this.path)
		{
			case "/user/add":
				request.setAttribute("update", "add");
				nom = request.getParameter("nom");
				prenom = request.getParameter("prenom");
				email = request.getParameter("email");
				login = request.getParameter("login");
				statut = request.getParameter("statut");
				password = request.getParameter("password");
				confirm_password = request.getParameter("confirm_password");
				registerValidator = new RegisterValidator(request, this.userManager, this.compteManager);
				result = registerValidator.validate();
				// System.out.println("RESULT ===> " + result);
				if (result.isEmpty())
				{
					EnumUserType status = "admin".equals(statut) ? EnumUserType.admin : EnumUserType.simple;
					user = new User(nom, prenom, email, status, new Compte(login, password));
					this.userManager.add(user);
					this.cookieManager.createCookie("userAdd", "L'utilisateur '" + login + "' inscrit !", 5);
					USER_LIST_URL = "admin".equals(statut) ? "/user/list-admin" : "/user/list";
					response.sendRedirect(request.getContextPath() + USER_LIST_URL);
				}
				else
				{
					form.put("nom", nom);
					form.put("prenom", prenom);
					form.put("email", email);
					form.put("login", login);
					form.put("mdp", password);
					form.put("cmdp", confirm_password);
					request.setAttribute("errors", result);
					request.setAttribute("form", form);
					request.getServletContext().getRequestDispatcher(USER_ADD_PAGE).forward(request, response);
				}
				break;
			case "/user/update":
				request.setAttribute("update", "update");
				nom = request.getParameter("nom");
				prenom = request.getParameter("prenom");
				email = request.getParameter("email");
				login = request.getParameter("login");
				statut = request.getParameter("statut");
				password = request.getParameter("password");
				confirm_password = request.getParameter("confirm_password");
				Long userId = Long.parseLong(request.getParameter("userId"));
				user = this.userManager.findUserById(userId);
				registerValidator = new RegisterValidator(request, this.userManager, this.compteManager, user);
				result = registerValidator.validate();
				System.out.println("RESULT =====> " + result);
				if (result.isEmpty())
				{
					EnumUserType status = "admin".equals(statut) ? EnumUserType.admin : EnumUserType.simple;
					user.setNom(nom);
					user.setPrenom(prenom);
					user.setEmail(email);
					user.setStatut(status);
					user.getCompte().setLogin(login);
					user.getCompte().setPassword(password);
					this.userManager.update(user);
					this.cookieManager.createCookie("userUpdate", "Edition de l'utilisateur '" + login + "' : OK!", 5);
					USER_LIST_URL = "admin".equals(statut) ? "/user/list-admin" : "/user/list";
					response.sendRedirect(request.getContextPath() + USER_LIST_URL);
				}
				else
				{
					form.put("userId", user.getId().toString());
					form.put("nom", nom);
					form.put("prenom", prenom);
					form.put("email", email);
					form.put("login", login);
					form.put("statut", user.getStatut().toString());
					form.put("mdp", password);
					form.put("cmdp", confirm_password);
					request.setAttribute("errors", result);
					request.setAttribute("form", form);
					request.getServletContext().getRequestDispatcher(USER_ADD_PAGE).forward(request, response);
				}
				break;
			case "/user/parametre":
				login = request.getParameter("login");
				String new_password = request.getParameter("new_password");
				confirm_password = request.getParameter("confirm_password");
				user = (User) request.getSession().getAttribute("user");
				ParametreValidator parametreValidator = new ParametreValidator(request, this.userManager);
				result = parametreValidator.validate();
				if (result.isEmpty())
				{
					user.getCompte().setLogin(login);
					user.getCompte().setPassword(new_password);
					this.userManager.update(user);
					this.cookieManager.createCookie("edit_params", "Edition des parametres de connexion : OK !", 5);
					response.sendRedirect(request.getContextPath() + "/user/parametre");
				}
				else
				{
					form.put("login", login);
					request.setAttribute("form", form);
					request.setAttribute("errors", result);
					request.getServletContext().getRequestDispatcher(USER_PARAMETER_PAGE).forward(request, response);
				}
				break;
			
		}
	}
	
}
