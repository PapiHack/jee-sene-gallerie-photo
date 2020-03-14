package sn.sgp.beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import sn.sgp.utils.EnumUserType;
import sn.sgp.utils.InheritColumns;

/**
 * @author papihack
 * @version 0.0.1
 * 
 *          Entity implementation class for Entity: User
 *
 */
@SuppressWarnings("serial")
@Entity
@NamedQuery(name = "getUserByEmail", query = "SELECT u FROM User u WHERE u.email = :userEmail")
@NamedQuery(name = "getUsersWithStatus", query = "SELECT u FROM User u WHERE u.statut = :userStatus")
@NamedQuery(name = "getAll", query = "SELECT u FROM User u")
@Table(name = "user")
@Stateful(passivationCapable = false)
public class User extends InheritColumns implements Serializable
{
	private String nom;
	private String prenom;
	private String email;
	private EnumUserType statut;
	@OneToOne(cascade =
	{ CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
	private Compte compte;
	@OneToMany(mappedBy = "proprio", cascade =
	{ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	private List<Album> albums;
	/*
	 * @ManyToOne(cascade = { CascadeType.MERGE }) private Album albumShared; //
	 * Album dont le user à accès => Navigabilité ne marche pas
	 */
	
	public User()
	{
	}
	
	public User(String nom, String prenom, String email, EnumUserType statut, Compte compte)
	{
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.statut = statut;
		this.compte = compte;
	}
	
	public User(Long id, String nom, String prenom, String email, EnumUserType statut, Compte compte)
	{
		super(id);
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.statut = statut;
		this.compte = compte;
	}
	
	public String getNom()
	{
		return this.nom;
	}
	
	public void setNom(String nom)
	{
		this.nom = nom;
	}
	
	public String getPrenom()
	{
		return this.prenom;
	}
	
	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public EnumUserType getStatut()
	{
		return this.statut;
	}
	
	public void setStatut(EnumUserType statut)
	{
		this.statut = statut;
	}
	
	public Compte getCompte()
	{
		return compte;
	}
	
	public void setCompte(Compte compte)
	{
		this.compte = compte;
	}
	
	public List<Album> getAlbums()
	{
		return albums;
	}
	
	public void setAlbums(List<Album> albums)
	{
		this.albums = albums;
	}
	/*
	 * public Album getAlbumShared() { return albumShared; }
	 * 
	 * public void setAlbumShared(Album albumShared) { this.albumShared =
	 * albumShared; }
	 */
	
}
