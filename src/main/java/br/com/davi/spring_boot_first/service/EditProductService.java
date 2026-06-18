package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.ProductEntity;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.ConflictException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.ProductRepository;
import br.com.davi.spring_boot_first.dto.response.EditProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
public class EditProductService {


    private final ProductRepository productRepository;


    public EditProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    private ProductEntity findId(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }


    @Transactional
    public EditProductResponse editProduct(Long id, String name, BigDecimal price, Integer quantity) {


        ProductEntity product = findId(id);

        List<ProductEntity> products = productRepository.findAll();


        if (name != null) {
            if (name.length() < 3 || name.length() > 50) {
                throw new BadRequestException("Product name needs to be between 3 and 50 characters");
            }
            product.setName(name);
        }


        for (ProductEntity p : products) {

            if  (p.getName().equals(name)) {
                throw new ConflictException("Another product with this name already exists");
            }

        }


        if (quantity != null) {
            if (quantity + product.getQuantity() < 0 || quantity > 100) {
                throw new BadRequestException("Product quantity needs to be between 0 and 100");
            }
            quantity += product.getQuantity();
            product.setQuantity(quantity);
        }


        if (price != null) {
            if (price.compareTo(BigDecimal.valueOf(5000)) >= 0 || price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new BadRequestException("Product price needs to be between 0 and 5000");
            }
            product.setPrice(price);
        }


        return new EditProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity()
        );

    }

}