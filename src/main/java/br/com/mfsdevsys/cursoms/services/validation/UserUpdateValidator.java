package br.com.mfsdevsys.cursoms.services.validation;

import br.com.mfsdevsys.cursoms.domain.User;
import br.com.mfsdevsys.cursoms.dto.UserUpdateDTO;
import br.com.mfsdevsys.cursoms.repositories.UserRepository;
import br.com.mfsdevsys.cursoms.resources.exceptions.FieldMessage;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

	private HttpServletRequest request ; // @Autowired
	private UserRepository repository;  // @Autowired

	public UserUpdateValidator(UserRepository repository, HttpServletRequest request) {
		this.repository = repository;
		this.request = request;
	}
	@Override
	public void initialize(UserUpdateValid ann) {
		
	}
	
	@Override
	public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {
		var uriVars = (Map<String,String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		long userId = Long.parseLong( uriVars.get("id"));

		List<FieldMessage> list = new ArrayList<>();
		
		// Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista
		User user = repository.findByEmail(dto.getEmail());
		if (user != null && userId != user.getId()) {
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
 