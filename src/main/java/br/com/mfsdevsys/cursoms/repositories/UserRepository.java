package br.com.mfsdevsys.cursoms.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mfsdevsys.cursoms.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	
	//Optional<User> findByFirstName(String firstName);
	//Optional<User> findByLastName(String lastName);
	
}
