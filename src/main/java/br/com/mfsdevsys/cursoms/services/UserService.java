package br.com.mfsdevsys.cursoms.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.mfsdevsys.cursoms.domain.Role;
import br.com.mfsdevsys.cursoms.domain.User;
import br.com.mfsdevsys.cursoms.dto.RoleDTO;
import br.com.mfsdevsys.cursoms.dto.UserDTO;
import br.com.mfsdevsys.cursoms.dto.UserInsertDTO;
import br.com.mfsdevsys.cursoms.repositories.RoleRepository;
import br.com.mfsdevsys.cursoms.repositories.UserRepository;
import br.com.mfsdevsys.cursoms.services.exceptions.DatabaseException;
import br.com.mfsdevsys.cursoms.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;
	

	
	@Transactional(readOnly=true)
	public Page<UserDTO> findAllPaged(Pageable pageable){
		
		Page<User> list = repository.findAll(pageable);
		return list.map(x -> new UserDTO(x));
		
	}
	
		
	@Transactional(readOnly=true)	
	public UserDTO findById(Long id) {
		
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(()-> new ResourceNotFoundException("Entity not found"));
		
		return new UserDTO(entity);
	}
	
	@Transactional
	public UserDTO insert(@RequestBody UserInsertDTO dto){
		User entity = new User();
		copyDtoToEntity(dto, entity);
		//entity.setPassword( passwordEncoder.encode( dto.getPassword()));
		entity = repository.save(entity) ;
		return new UserDTO(entity);
	}
	
	
	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
		try {
			User entity = repository.getOne(id) ;
			// User entity = repository.getReferenceById(id); # 2.7.6
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new UserDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found "+id);
		}

	}

	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found "+id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	private void copyDtoToEntity(UserDTO dto, User entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		entity.setStatus(dto.getStatus());
	
		
		entity.getRoles().clear();
		for (RoleDTO roleDTO: dto.getRoles()) {
			//Role role = roleRepository.getReferenceById(roleDTO.getId()) ;
			Role role = roleRepository.getOne(roleDTO.getId()) ;
			entity.getRoles().add(role);
		}
	}
	
}

