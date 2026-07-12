package br.com.davi.spring_boot_first.repository;

import br.com.davi.spring_boot_first.entity.ConcludedItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ConcludedRepository extends JpaRepository<ConcludedItemsEntity, Long> {

    List<ConcludedItemsEntity> findByOrderId(Long orderId);

}