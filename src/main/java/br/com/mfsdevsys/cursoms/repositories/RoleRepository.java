package br.com.mfsdevsys.cursoms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mfsdevsys.cursoms.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
