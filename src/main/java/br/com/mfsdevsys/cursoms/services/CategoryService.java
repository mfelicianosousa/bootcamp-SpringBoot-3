package br.com.mfsdevsys.cursoms.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mfsdevsys.cursoms.domain.Category;
import br.com.mfsdevsys.cursoms.dto.CategoryDTO;
import br.com.mfsdevsys.cursoms.repositories.CategoryRepository;
import br.com.mfsdevsys.cursoms.services.exceptions.DatabaseException;
import br.com.mfsdevsys.cursoms.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	
	private CategoryRepository repository;	// @Autowired
	
	public CategoryService(CategoryRepository repository) {
		this.repository = repository;
	}
	
	@Transactional(readOnly=true)	
	public CategoryDTO findById(Integer id) {
		
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(()-> new ResourceNotFoundException("Entity not found"));
		
		return new CategoryDTO(entity);
	}
	
	@Transactional(readOnly=true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest){
		
		Page<Category> list = repository.findAll(pageRequest);
		return list.map(x -> new CategoryDTO(x));
		
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Integer id, CategoryDTO dto) {
		try {
		//Category entity = repository.getReferenceById(id);
		Category entity = repository.getOne(id);
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found "+id);
		}

	}

	public void delete(Integer id) {
		try {
		repository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found "+id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}

