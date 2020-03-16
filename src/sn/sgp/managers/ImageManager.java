package sn.sgp.managers;

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
	
}
