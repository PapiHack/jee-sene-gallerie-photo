package sn.sgp.managers;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import sn.sgp.beans.User;
import sn.sgp.utils.EnumUserType;

/**
 * 
 * @author papihack
 * @version 0.0.1
 * 
 *          Classe faisant office de gestionnaire des entités User
 *
 */
@Stateless
public class UserManager
{
	@PersistenceContext(name = "sgp_pu")
	private EntityManager em;
	
	public void add(User user)
	{
		this.em.persist(user);
	}
	
	public User findUserByEmail(String email)
	{
		User user = null;
		try
		{
			user = (User) this.em.createNamedQuery("getUserByEmail").setParameter("userEmail", email).getSingleResult();
			System.out.println("USER => " + user.getPrenom() + " " + user.getNom());
		} catch (NoResultException e)
		{
			System.out.println("User with " + email + " email address does not exist !");
		}
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findUsersWithStatus(EnumUserType status)
	{
		List<User> users = null;
		users = (List<User>) this.em.createNamedQuery("getUsersWithStatus").setParameter("userStatus", status)
				.getResultList();
		
		return users;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAll()
	{
		return (List<User>) this.em.createNamedQuery("getAll").getResultList();
	}
	
	public List<User> findUsersWithout(String email)
	{
		List<User> users = this.findAll();
		
		for (User user : users)
		{
			if (user.getEmail().equals(email))
			{
				users.remove(user);
				break;
			}
		}
		
		return users;
	}
	
	public void delete(User user)
	{
		this.em.remove(user);
	}
	
	public void deleteById(Long id)
	{
		this.em.remove(this.em.find(User.class, id));
	}
	
	public User findUserById(Long id)
	{
		return this.em.find(User.class, id);
	}
	
	public void update(User user)
	{
		this.em.merge(user);
	}
	
}
