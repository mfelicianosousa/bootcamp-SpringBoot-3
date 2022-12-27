package br.com.mfsdevsys.cursoms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mfsdevsys.cursoms.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	//Optional<Product> findByCodBarra(String codBarra);
}
