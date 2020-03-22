package sn.sgp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author papihack
 * @version 0.0.1
 * 
 *          Servlet permettant de gérer la déconnexion des utilisateurs
 */
@SuppressWarnings("serial")
@WebServlet("/user/deconnexion")
public class LogoutController extends HttpServlet
{
	
	private static final String HOME_PAGE = "/home";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + HOME_PAGE);
	}
	
}
