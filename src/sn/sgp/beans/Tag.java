package sn.sgp.beans;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import sn.sgp.utils.InheritColumns;

/**
 * @author papihack
 * @version 0.0.1
 * 
 * 
 *          Entity implementation class for Entity: Tag
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tag")
public class Tag extends InheritColumns implements Serializable
{
	private String nom;
	@ManyToOne(cascade =
	{ CascadeType.MERGE })
	private Image image;
	
	public Tag()
	{
	}
	
	public Tag(String nom)
	{
		this.nom = nom;
	}
	
	public Tag(Long id, String nom)
	{
		super(id);
		this.nom = nom;
	}
	
	public String getNom()
	{
		return this.nom;
	}
	
	public void setNom(String nom)
	{
		this.nom = nom;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public void setImage(Image image)
	{
		this.image = image;
	}
	
}
