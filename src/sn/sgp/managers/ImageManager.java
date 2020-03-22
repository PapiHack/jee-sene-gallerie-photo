package sn.sgp.managers;

import java.io.File;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import sn.sgp.beans.Image;

/**
 * 
 * @author papihack
 * @version 0.0.1
 * 
 *          Classe faisant office de gestionnaire des entités Image
 *
 */
@Stateless
public class ImageManager
{
	@PersistenceContext(name = "sgp_pu")
	private EntityManager em;
	
	public void add(Image image)
	{
		this.em.persist(image);
	}
	
	public void remove(Image image, String uplodaDirectory)
	{
		this.removeImageFile(uplodaDirectory, image);
		this.em.remove(image);
	}
	
	public void update(Image image)
	{
		this.em.merge(image);
	}
	
	private void removeImageFile(String path, Image img)
	{
		File imgFile = new File(path + "/" + img.getFichierImage());
		if (imgFile.exists())
		{
			imgFile.delete();
		}
	}
	
}
