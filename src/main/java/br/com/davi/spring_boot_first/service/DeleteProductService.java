package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.ProductEntity;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DeleteProductService {


    private final ProductRepository productRepository;


    public DeleteProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    private ProductEntity findId(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }


    @Transactional
    public Long deleteProduct(Long id) {

        ProductEntity product = findId(id);
        productRepository.delete(product);

        return id;

    }
}