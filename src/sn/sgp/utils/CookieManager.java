package sn.sgp.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author papihack
 * @version 0.0.1
 * 
 *          Classe utilitaire facilitant la gestion des cookies au niveau de
 *          l'application.
 *
 */
public class CookieManager
{
	private HttpServletResponse response;
	private HttpServletRequest request;
	
	public CookieManager(HttpServletRequest request, HttpServletResponse response)
	{
		this.response = response;
		this.request = request;
	}
	
	public void createCookie(String nom, String valeur, int maxAge)
	{
		Cookie cookie = new Cookie(nom, valeur);
		cookie.setMaxAge(maxAge);
		this.response.addCookie(cookie);
	}
	
	public String getCookieValue(String nom)
	{
		Cookie[] cookies = this.request.getCookies();
		if (cookies != null)
		{
			for (Cookie cookie : cookies)
			{
				if (cookie != null && nom.equals(cookie.getName()))
				{
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
