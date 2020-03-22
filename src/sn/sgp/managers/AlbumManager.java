package sn.sgp.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import sn.sgp.beans.Album;
import sn.sgp.beans.Image;
import sn.sgp.beans.User;
import sn.sgp.utils.EnumAlbumStatus;

/**
 * 
 * @author papihack
 * @version 0.0.1
 * 
 *          Classe faisant office de gestionnaire des entités Album
 *
 */
@Stateless
public class AlbumManager
{
	@PersistenceContext(name = "sgp_pu")
	private EntityManager em;
	
	public Album findById(Long id)
	{
		return this.em.find(Album.class, id);
	}
	
	public void add(Album album)
	{
		this.em.persist(album);
	}
	
	public void delete(Album album)
	{
		this.em.remove(album);
	}
	
	public void deleteById(Long id, HttpServletRequest request)
	{
		Album album = this.em.find(Album.class, id);
		String uploadDirectory = request.getServletContext().getInitParameter("uploadDirectory");
		this.removeImagesOnDeleteAlbum(album, uploadDirectory);
		this.em.remove(album);
	}
	
	public void update(Album album)
	{
		this.em.merge(album);
	}
	
	@SuppressWarnings("unchecked")
	public List<Album> findUserAlbum(User user)
	{
		List<Album> albums = null;
		
		albums = this.em.createNamedQuery("getUserAlbums").setParameter("proprio", user).getResultList();
		
		return albums;
	}
	
	@SuppressWarnings("unchecked")
	public List<Album> findAlbumByStatus(EnumAlbumStatus status)
	{
		List<Album> albums = null;
		
		albums = this.em.createNamedQuery("getAlbumByStatus").setParameter("statut", status).getResultList();
		
		return albums;
	}
	
	private void removeImagesOnDeleteAlbum(Album album, String path)
	{
		for (Image img : album.getImages())
		{
			File imgFile = new File(path + "/" + img.getFichierImage());
			if (imgFile.exists())
			{
				imgFile.delete();
			}
		}
	}
	
	public List<Album> getAccessibleAlbums(User user)
	{
		List<Album> albums = this.findAlbumByStatus(EnumAlbumStatus.privé);
		List<Album> accessibleAlbums = new ArrayList<Album>();
		for (Album album : albums)
		{
			for (User u : album.getSharedWith())
			{
				if (u.getEmail().equals(user.getEmail()))
				{
					accessibleAlbums.add(album);
				}
			}
		}
		
		return accessibleAlbums;
	}
	
}
