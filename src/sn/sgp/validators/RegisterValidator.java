package sn.sgp.validators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import sn.sgp.beans.User;
import sn.sgp.managers.CompteManager;
import sn.sgp.managers.UserManager;

/**
 * 
 * @author papihack
 * @version 0.0.1
 * 
 *          Classe permettant de valider le formulaire d'inscription (ainsi que
 *          celui d'ajout et de mis à jour) des utilisateurs.
 *
 */
public class RegisterValidator
{
	
	private HashMap<String, String> result = new HashMap<String, String>();
	private final String NAME_PATTERN = "[A-Za-zéèöôîï]{2,}";
	private final String LOGIN_PATTERN = "[A-Za-z0-9éèöôîï\\.\\-,]{2,}";
	private final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
	private String NOM;
	private String PRENOM;
	private String EMAIL;
	private String LOGIN;
	private String PASSWORD;
	private String PASSWORD_CONFIRM;
	private UserManager userManager;
	private CompteManager compteManager;
	private User user = null;
	private HttpServletRequest request;
	
	public RegisterValidator(HttpServletRequest request, UserManager userManager, CompteManager compteManager)
	{
		this.request = request;
		this.NOM = request.getParameter("nom");
		this.PRENOM = request.getParameter("prenom");
		this.EMAIL = request.getParameter("email") == null ? "" : request.getParameter("email");
		this.LOGIN = request.getParameter("login");
		this.PASSWORD = request.getParameter("password") == null || request.getParameter("password").isEmpty() ? ""
				: request.getParameter("password");
		this.PASSWORD_CONFIRM = request.getParameter("confirm_password") == null
				|| request.getParameter("confirm_password").isEmpty() ? "a" : request.getParameter("confirm_password");
		this.userManager = userManager;
		this.compteManager = compteManager;
	}
	
	public RegisterValidator(HttpServletRequest request, UserManager userManager, CompteManager compteManager,
			User user)
	{
		this(request, userManager, compteManager);
		this.user = user;
	}
	
	public HashMap<String, String> validate()
	{
		
		if (!this.isValidName(this.NOM))
		{
			this.result.put("nom", "Le nom doit comporter au minimum deux (2) caractères.");
		}
		if (!this.isValidName(this.PRENOM))
		{
			this.result.put("prenom", "Le prénom doit comporter au minimum deux (2) caractères.");
		}
		if (!this.isValidEmail())
		{
			this.result.put("email", "Veuillez saisir une adresse email valide.");
		}
		if (!this.isValidLogin())
		{
			this.result.put("login", "Le login ou nom d'utilisateur doit comporter au minimum deux (2) caractères.");
		}
		if ("update".equals(this.request.getAttribute("update")))
		{
			List<String> messages = this.checkLoginMailWhenUpdateUser();
			if (messages.size() > 0)
			{
				for (String msg : messages)
				{
					String tab[] = msg.split(",");
					this.result.put(tab[0], tab[1]);
				}
			}
		}
		else
		{
			if (this.userManager.findUserByEmail(this.EMAIL) != null)
			{
				this.result.put("email", "Cette adresse email existe déjà.");
			}
			if (this.compteManager.findUserByLogin(this.LOGIN) != null)
			{
				this.result.put("login", "Ce login ou nom d'utilisateur existe déjà.");
			}
		}
		if (!this.isPasswordValid())
		{
			this.result.put("password", "Les mots de passe ne sont pas conformes.");
		}
		if (this.isFormEmpty())
		{
			this.result.put("vide", "Veuillez remplir tous les champs.");
		}
		
		return this.result;
	}
	
	private boolean isPasswordValid()
	{
		return this.PASSWORD.equals(PASSWORD_CONFIRM);
	}
	
	private boolean isValidEmail()
	{
		return this.EMAIL.matches(this.EMAIL_PATTERN);
	}
	
	private String removeSpaces(String in)
	{
		return in.replaceAll(" ", "");
	}
	
	private boolean isFormEmpty()
	{
		if (this.NOM.isEmpty() || this.PRENOM.isEmpty() || this.EMAIL.isEmpty() || this.LOGIN.isEmpty()
				|| this.PASSWORD.isEmpty() || this.PASSWORD_CONFIRM.isEmpty())
		{
			return true;
		}
		return false;
	}
	
	private boolean isValidLogin()
	{
		return this.LOGIN.matches(this.LOGIN_PATTERN);
	}
	
	private boolean isValidName(String name)
	{
		return this.removeSpaces(name.trim()).matches(this.NAME_PATTERN);
	}
	
	private List<String> checkLoginMailWhenUpdateUser()
	{
		List<User> users = this.userManager.findAll();
		for (User u : users)
		{
			if (u.getId() == this.user.getId())
			{
				users.remove(u);
				break;
			}
		}
		List<String> messages = new ArrayList<String>();
		for (User user : users)
		{
			if (user.getEmail().equals(this.EMAIL))
			{
				messages.add("email, Cette adresse email existe déjà.");
			}
			if (user.getCompte().getLogin().equals(this.LOGIN))
			{
				messages.add("login, Ce login ou nom d'utilisateur existe déjà.");
			}
		}
		return messages;
	}
	
}
