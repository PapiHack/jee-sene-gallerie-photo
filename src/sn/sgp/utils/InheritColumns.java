package sn.sgp.utils;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * @author papihack
 * @version 0.0.1
 * 
 *          Classe utilitaire contenant des propriétés communes à la presque
 *          totalité des entités.
 */
@MappedSuperclass
public class InheritColumns
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	public InheritColumns()
	{
		
	}
	
	public InheritColumns(Long id)
	{
		this.id = id;
	}
	
	public InheritColumns(Long id, LocalDateTime createdAt, LocalDateTime updatedAt)
	{
		this.id = id;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	@PrePersist
	public void init()
	{
		this.createdAt = LocalDateTime.now();
	}
	
	@PreUpdate
	public void update()
	{
		this.updatedAt = LocalDateTime.now();
	}
	
	public Long getId()
	{
		return this.id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public LocalDateTime getCreatedAt()
	{
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt)
	{
		this.createdAt = createdAt;
	}
	
	public LocalDateTime getUpdatedAt()
	{
		return updatedAt;
	}
	
	public void setUpdatedAt(LocalDateTime updatedAt)
	{
		this.updatedAt = updatedAt;
	}
	
}
