package sn.sgp.validators;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @author papihack
 * @version 0.0.1
 * 
 *          Servlet permettant de gérer la validation des formulaires d'ajout et
 *          d'édition d'image.
 */
public class ImageValidator
{
	private String TITRE;
	private String DESCRIPTION;
	private String LARGEUR;
	private String HAUTEUR;
	private HttpServletRequest request;
	private HashMap<String, String> result = new HashMap<String, String>();
	private final String STR_PATTERN = "[A-Za-zéèöôîï]{2,}";
	private final String DIMENSION_PATTERN = "[0-9]+";
	
	public ImageValidator(HttpServletRequest request)
	{
		this.request = request;
		this.TITRE = this.request.getParameter("titre") == null ? "" : this.request.getParameter("titre");
		this.HAUTEUR = this.request.getParameter("hauteur") == null ? "" : this.request.getParameter("hauteur");
		this.LARGEUR = this.request.getParameter("largeur") == null ? "" : this.request.getParameter("largeur");
		this.DESCRIPTION = this.request.getParameter("description") == null ? ""
				: this.request.getParameter("description");
	}
	
	public HashMap<String, String> validate()
	{
		if (this.isEmptyForm())
		{
			this.result.put("vide", "Veuillez remplir tous les champs");
		}
		if (!this.isValidTitle())
		{
			this.result.put("titre", "Le titre doit comporter au minimum deux (2) caractères.");
		}
		if (!this.isValidLargeur())
		{
			this.result.put("largeur", "La largeur doit comporter que des chiffres.");
		}
		if (!this.isValidHauteur())
		{
			this.result.put("hauteur", "La hauteur doit comporter que des chiffres.");
		}
		if (!this.isValidDescription())
		{
			this.result.put("description", "La description doit comporter au minimum deux (2) caractères.");
		}
		return this.result;
	}
	
	private boolean isValidTitle()
	{
		return this.removeSpaces(TITRE.trim()).matches(STR_PATTERN);
	}
	
	private boolean isValidDescription()
	{
		return this.removeSpaces(DESCRIPTION.trim()).matches(STR_PATTERN);
	}
	
	private boolean isValidHauteur()
	{
		return this.removeSpaces(HAUTEUR.trim()).matches(DIMENSION_PATTERN);
	}
	
	private boolean isValidLargeur()
	{
		return this.removeSpaces(LARGEUR.trim()).matches(DIMENSION_PATTERN);
	}
	
	private boolean isEmptyForm()
	{
		if (this.TITRE.isEmpty() || this.DESCRIPTION.isEmpty())
		{
			return true;
		}
		return false;
	}
	
	private String removeSpaces(String in)
	{
		return in.replaceAll(" ", "");
	}
	
}
