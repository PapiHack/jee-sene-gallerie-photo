package sn.sgp.validators;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import sn.sgp.beans.User;
import sn.sgp.managers.UserManager;

/**
 * 
 * @author papihack
 * @version 0.0.1
 * 
 *          Classe permettant de valider le formulaire de mis à jour des
 *          paramètres de connexion.
 *
 */
public class ParametreValidator
{
	private HttpServletRequest request;
	private String LOGIN;
	private String OLD_PASSWORD;
	private String NEW_PASSWORD;
	private String NEW_PASSWORD_CONFIRM;
	private User user;
	private UserManager userManager;
	private HashMap<String, String> result = new HashMap<String, String>();
	
	public ParametreValidator(HttpServletRequest request, UserManager userManager)
	{
		this.request = request;
		this.userManager = userManager;
		this.user = (User) this.request.getSession().getAttribute("user");
		this.LOGIN = this.request.getParameter("login");
		this.OLD_PASSWORD = this.request.getParameter("old_password");
		this.NEW_PASSWORD = this.request.getParameter("new_password");
		this.NEW_PASSWORD_CONFIRM = this.request.getParameter("confirm_password");
	}
	
	public HashMap<String, String> validate()
	{
		if (this.isFormEmpty())
		{
			this.result.put("vide", "Veuillez remplir tous les champs.");
		}
		if (this.isLoginExist())
		{
			this.result.put("login", "Ce login ou nom d'utilisateur existe déjà.");
		}
		if (!this.isOldPasswordValid())
		{
			this.result.put("old_password", "Ce mot de passe ne correspond pas à votre actuel mot de passe.");
		}
		if (!this.isPasswordsValid())
		{
			this.result.put("password", "Les mots ne sont pas conformes.");
		}
		return this.result;
	}
	
	private boolean isFormEmpty()
	{
		if (this.LOGIN == null || this.LOGIN.isEmpty() || this.OLD_PASSWORD == null || this.OLD_PASSWORD.isEmpty()
				|| this.NEW_PASSWORD == null || this.NEW_PASSWORD.isEmpty() || this.NEW_PASSWORD_CONFIRM == null
				|| this.NEW_PASSWORD_CONFIRM.isEmpty())
		{
			return true;
		}
		return false;
	}
	
	private boolean isOldPasswordValid()
	{
		return this.user.getCompte().getPassword().equals(this.OLD_PASSWORD);
	}
	
	private boolean isPasswordsValid()
	{
		return this.NEW_PASSWORD.equals(this.NEW_PASSWORD_CONFIRM);
	}
	
	private boolean isLoginExist()
	{
		List<User> users = this.userManager.findAll();
		this.user = this.userManager.findUserByEmail(this.user.getEmail());
		for (User u : users)
		{
			if (u.getId() == this.user.getId())
			{
				users.remove(u);
				break;
			}
		}
		for (User user : users)
		{
			if (user.getCompte().getLogin().equals(this.LOGIN))
			{
				return true;
			}
		}
		return false;
	}
	
}
