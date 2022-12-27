package br.com.mfsdevsys.cursoms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mfsdevsys.cursoms.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
