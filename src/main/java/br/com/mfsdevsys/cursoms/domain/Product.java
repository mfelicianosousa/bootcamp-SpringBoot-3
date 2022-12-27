package br.com.mfsdevsys.cursoms.domain;


import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="product")
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, length=60)
	private String name;
	
	@Column(columnDefinition="TEXT", nullable=true )
	private String description;
	
	@Column(name="sale_price")
	private Double salePrice;
	
	@Column(name="unit_measure",nullable=false, length=3)
	private String unitOfMeasure;
	
	@Column(nullable=true, length=100)
	private String imageUrl;
	
	@Column(columnDefinition ="TIMESTAMP WITHOUT TIME ZONE")
	private Instant createdAt;
	
	@Column(columnDefinition ="TIMESTAMP WITHOUT TIME ZONE")
	private Instant updatedAt;
	
	@JsonBackReference
	@ManyToMany
	@JoinTable(name="product_category",
	   joinColumns=@JoinColumn(name="product_id"),
	   inverseJoinColumns=@JoinColumn(name="category_id"))
	private Set<Category> categories = new HashSet<>();
	
	public Product() {		
	}
	
	public Product(Long id, 
			String name, 
			String description, 
			Double salePrice, 
			String unitOfMeasure,
			String imageUrl
		) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.salePrice = salePrice;
		this.unitOfMeasure = unitOfMeasure;
		this.imageUrl = imageUrl;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	@PrePersist
	public void prePersist() {
		createdAt = Instant.now();
	}
	
	@PreUpdate
	public void preUpdate() {
		updatedAt = Instant.now();
	}
	
	public Set<Category> getCategories() {
		return categories;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}
}

