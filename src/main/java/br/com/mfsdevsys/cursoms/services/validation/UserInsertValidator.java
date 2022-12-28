package br.com.mfsdevsys.cursoms.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.mfsdevsys.cursoms.domain.User;
import br.com.mfsdevsys.cursoms.dto.UserInsertDTO;
import br.com.mfsdevsys.cursoms.repositories.UserRepository;
import br.com.mfsdevsys.cursoms.resources.exceptions.FieldMessage;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {
	
	private UserRepository repository;  // @Autowired
	
	public UserInsertValidator( UserRepository repository) {
		this.repository = repository;
	}
	
	
	@Override
	public void initialize(UserInsertValid ann) {
		
	}
	
	@Override
	public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		// Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista
		User user = repository.findByEmail(dto.getEmail());
		if (user != null) {
			list.add(new FieldMessage("email","Email já existe"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
			      .addConstraintViolation();
		}
		return list.isEmpty();
	}

}
 