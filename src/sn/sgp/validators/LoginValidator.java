package sn.sgp.validators;

import javax.servlet.http.HttpServletRequest;

import sn.sgp.beans.Compte;
import sn.sgp.managers.CompteManager;

/**
 * 
 * @author papihack
 * @version 0.0.1
 * 
 *          Classe permettant de valider le formulaire de connexion.
 *
 */
public class LoginValidator
{
	private CompteManager compteManager;
	private String LOGIN;
	private String PASSWORD;
	
	public LoginValidator(HttpServletRequest request, CompteManager cm)
	{
		this.compteManager = cm;
		this.LOGIN = request.getParameter("login");
		this.PASSWORD = request.getParameter("password");
	}
	
	public boolean validate()
	{
		return this.isFormValid() && this.canBeConnected();
	}
	
	private boolean isFormValid()
	{
		boolean valid = true;
		
		if (this.LOGIN == null || this.PASSWORD == null || this.LOGIN.isEmpty() || this.PASSWORD.isEmpty())
		{
			valid = false;
		}
		
		return valid;
	}
	
	private boolean canBeConnected()
	{
		Compte compte = this.compteManager.findUserByLogin(this.LOGIN);
		return compte != null && compte.getPassword().equals(this.PASSWORD);
	}
	
}
