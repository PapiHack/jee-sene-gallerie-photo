package sn.sgp.managers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import sn.sgp.beans.Compte;

/**
 * 
 * @author papihack
 * @version 0.0.1
 * 
 *          Classe faisant office de gestionnaire des entités Compte
 *
 */
@Stateless
public class CompteManager
{
	@PersistenceContext(name = "sgp_pu")
	private EntityManager em;
	
	public Compte findUserByLogin(String login)
	{
		Compte compte = null;
		try
		{
			compte = (Compte) this.em.createNamedQuery("getCompteByLogin").setParameter("compteLogin", login)
					.getSingleResult();
			// System.out.println("Compte => " + compte.getLogin());
		} catch (NoResultException e)
		{
			System.out.println("Compte with " + login + " does not exist !");
		}
		return compte;
	}
}
