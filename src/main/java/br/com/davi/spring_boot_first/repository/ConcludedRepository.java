package br.com.davi.spring_boot_first.repository;

import br.com.davi.spring_boot_first.entity.ConcludedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ConcludedRepository extends JpaRepository<ConcludedEntity, Long> {

    List<ConcludedEntity> findByOrderId(Long orderId);

}