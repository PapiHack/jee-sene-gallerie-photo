package sn.sgp.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import sn.sgp.beans.Image;

public class UploadImage
{
	private HashMap<String, String> errors = new HashMap<String, String>();
	private String UPLOAD_DIRECTORY;
	private static final int TAILLE_TAMPON = 10240;
	private String resultat;
	private HttpServletRequest request;
	
	public UploadImage()
	{
		
	}
	
	public UploadImage(HttpServletRequest request)
	{
		this.request = request;
		String upload = this.request.getServletContext().getRealPath("/uploads");
		String uploadDirectory = new File(upload).getAbsolutePath();
		File directory = new File(uploadDirectory);
		if (!directory.exists())
		{
			directory.mkdir();
		}
		this.UPLOAD_DIRECTORY = uploadDirectory;
	}
	
	public HashMap<String, String> getErrors()
	{
		return this.errors;
	}
	
	public String getResultat()
	{
		return this.resultat;
	}
	
	private String getFileName(final Part part)
	{
		for (String content : part.getHeader("content-disposition").split(";"))
		{
			if (content.trim().startsWith("filename"))
			{
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
	
	public List<Image> saveImages()
	{
		List<Image> images = new ArrayList<Image>();
		Collection<Part> parts = null;
		try
		{
			parts = this.request.getParts();
		} catch (IllegalStateException e)
		{
			this.errors.put("taille", "Les données envoyées sont trop volumineuses.");
			e.printStackTrace();
		} catch (IOException e)
		{
			this.errors.put("serveur", "Erreur de configuration du serveur.");
			e.printStackTrace();
		} catch (ServletException e)
		{
			this.errors.put("method",
					"Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier.");
			e.printStackTrace();
		}
		
		if (this.errors.isEmpty())
		{
			for (Part part : parts)
			{
				String nomFichier = this.getFileName(part);
				InputStream contenuFichier = null;
				if (nomFichier != null && !nomFichier.isEmpty())
				{
					nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
							.substring(nomFichier.lastIndexOf('\\') + 1);
					try
					{
						contenuFichier = part.getInputStream();
					} catch (IOException e)
					{
						this.errors.put("readError", "Une erreur est survenue lors de la lecture de votre fichier.");
						e.printStackTrace();
					}
					if (this.errors.isEmpty())
					{
						try
						{
							this.writeFile(contenuFichier, nomFichier);
							Image img = new Image();
							img.setTitre("Titre de l'image " + nomFichier);
							img.setDescription("Description de l'image " + nomFichier);
							img.setHauteur("600");
							img.setLargeur("850");
							img.setFichierImage(nomFichier);
							images.add(img);
						} catch (IOException e)
						{
							this.errors.put("writeError", "Erreur lors de l'écriture du fichier.");
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		this.resultat = this.errors.isEmpty() ? "Upload effectué avec succès !" : "Echec de l'upload !";
		
		return images;
	}
	
	private void writeFile(InputStream contenu, String nomFichier) throws IOException
	{
		BufferedInputStream entree = null;
		BufferedOutputStream sortie = null;
		try
		{
			// System.out.println("DANS LE BLOC TRY DU WRITEFILE
			// ----------------------------");
			entree = new BufferedInputStream(contenu, TAILLE_TAMPON);
			sortie = new BufferedOutputStream(new FileOutputStream(new File(UPLOAD_DIRECTORY + "/" + nomFichier)),
					TAILLE_TAMPON);
			byte[] tampon = new byte[TAILLE_TAMPON];
			int longueur = 0;
			while ((longueur = entree.read(tampon)) > 0)
			{
				sortie.write(tampon, 0, longueur);
			}
		} finally
		{
			try
			{
				sortie.close();
			} catch (IOException ignore)
			{
			}
			try
			{
				entree.close();
			} catch (IOException ignore)
			{
			}
		}
		
	}
	
	public String getValeur(Part part) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
		StringBuilder valeur = new StringBuilder();
		char[] buffer = new char[1024];
		int longueur = 0;
		while ((longueur = reader.read(buffer)) > 0)
		{
			valeur.append(buffer, 0, longueur);
		}
		return valeur.toString();
	}
	
}
