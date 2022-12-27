package br.com.mfsdevsys.cursoms.dto;


import java.io.Serializable;

import br.com.mfsdevsys.cursoms.domain.Category;

public class CategoryDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	
	public CategoryDTO() {
	
	}

	public CategoryDTO(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public CategoryDTO(Category entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

