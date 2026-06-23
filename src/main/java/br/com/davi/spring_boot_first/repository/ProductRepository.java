package br.com.davi.spring_boot_first.repository;

import br.com.davi.spring_boot_first.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    boolean existsByName(String name);

}