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
 *          Filtre permettant de restreindre l'accés aux ressources qui
 *          nécessitent une authentification.
 */
@WebFilter(description = "Permet de restreindre l'accés aux ressources qui nécessitent une authentification.", urlPatterns =
{ "/user/*" })
public class RestrictionFilter implements Filter
{
	
	private static final String LOGIN_URL = "/login";
	
	/**
	 * Default constructor.
	 */
	public RestrictionFilter()
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
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		if (req.getSession().getAttribute("user") == null)
		{
			res.sendRedirect(req.getContextPath() + LOGIN_URL);
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
		// TODO Auto-generated method stub
	}
	
}
