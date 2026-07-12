package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.ProductEntity;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DeleteProductService {


    private final ProductRepository productRepository;


    public DeleteProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    private ProductEntity findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    
    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public Long deleteProduct(Long id) {

        ProductEntity product = findProductById(id);
        productRepository.delete(product);

        return id;

    }

}