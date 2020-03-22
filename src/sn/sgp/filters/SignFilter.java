package sn.sgp.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author papihack
 * @version 0.0.1
 * 
 *          Filtre permettant de rediriger le user connecté vers sa page
 *          d'accueil lorsqu'il essai d'accéder à la page de connexion ou
 *          d'inscritpion.
 */
@WebFilter(description = "Permet de rediriger le user connecté vers sa page d'accueil lorsqu'il essai d'accéder à la page de connexion ou d'inscritpion", urlPatterns =
{ "/login", "/register" })
public class SignFilter implements Filter
{
	
	private static final String REDIRECT_URL = "/user/home";
	
	/**
	 * Default constructor.
	 */
	public SignFilter()
	{
	}
	
	/**
	 * @see Filter#destroy()
	 */
	public void destroy()
	{
	}
	
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		
		if (req.getSession().getAttribute("user") != null)
		{
			res.sendRedirect(req.getContextPath() + REDIRECT_URL);
		}
		else
		{
			chain.doFilter(req, res);
		}
	}
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException
	{
	}
	
}
