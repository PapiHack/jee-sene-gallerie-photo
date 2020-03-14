package sn.sgp.beans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import sn.sgp.utils.EnumAlbumStatus;
import sn.sgp.utils.InheritColumns;

/**
 * @author papihack
 * @version 0.0.1
 * 
 *          Entity implementation class for Entity: Album
 *
 */
@SuppressWarnings("serial")
@Entity
@NamedQuery(name = "getUserAlbums", query = "SELECT a FROM Album a WHERE a.proprio = :proprio")
@NamedQuery(name = "getAlbumByStatus", query = "SELECT a FROM Album a WHERE a.statut = :statut")
@Table(name = "album")
public class Album extends InheritColumns implements Serializable
{
	
	private String titre;
	private String description;
	private EnumAlbumStatus statut;
	@OneToMany(mappedBy = "album", cascade =
	{ CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
	private List<Image> images;
	@ManyToOne
	private User proprio;
	@ManyToMany(cascade =
	{ CascadeType.MERGE })
	private List<User> sharedWith;
	
	public Album()
	{
	}
	
	public Album(String titre, String description, EnumAlbumStatus statut)
	{
		this.titre = titre;
		this.description = description;
		this.statut = statut;
	}
	
	public String getTitre()
	{
		return this.titre;
	}
	
	public void setTitre(String titre)
	{
		this.titre = titre;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public EnumAlbumStatus getStatut()
	{
		return statut;
	}
	
	public void setStatut(EnumAlbumStatus statut)
	{
		this.statut = statut;
	}
	
	public List<Image> getImages()
	{
		return images;
	}
	
	public void setImages(List<Image> images)
	{
		this.images = images;
	}
	
	public User getProprio()
	{
		return proprio;
	}
	
	public void setProprio(User proprio)
	{
		this.proprio = proprio;
	}
	
	public List<User> getSharedWith()
	{
		return sharedWith;
	}
	
	public void setSharedWith(List<User> sharedWith)
	{
		this.sharedWith = sharedWith;
	}
	
}
