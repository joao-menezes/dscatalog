package com.joaomenezes.dscatalog.entities;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.*;


//Serializable permite o obj 
//ser convertido em um sequecia de BITES
@Entity
@Table(name = "tb_category")
public class Category implements Serializable{
	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant dateCreated;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant dateUpdated;

	public Category() {}

	public Category(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getDateCreated() {
		return dateCreated;
	}

	public Instant getDateUpdated() {
		return dateUpdated;
	}

	@PrePersist
	public void prePersist() {
		dateCreated = Instant.now();
	}

	@PreUpdate
	public void preUpdated() {
		dateUpdated = Instant.now();
	}
	//ambos comparam os valores de dois objs (categoria) pelo ID
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	//compração mais acirrada
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
}
