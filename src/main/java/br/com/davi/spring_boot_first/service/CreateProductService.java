package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.ProductEntity;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.ConflictException;
import br.com.davi.spring_boot_first.repository.ProductRepository;
import br.com.davi.spring_boot_first.dto.response.CreateProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class CreateProductService {

    private final ProductRepository productRepository;


    public CreateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Transactional
    public CreateProductResponse createNewProduct(String name, BigDecimal price, int quantity) {


        ProductEntity product = new ProductEntity();

        List<ProductEntity> products = productRepository.findAll();


        if (name.isBlank()) {
            throw new BadRequestException("Name is required");
        }

        for (ProductEntity p : products) {

            if  (p.getName().equals(name)) {
                throw new ConflictException("Another product with this name already exists");
            }

        }


        if (price.compareTo(BigDecimal.valueOf(5000)) > 0 || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Price must be between 0 and 5000");
        }

        if (quantity <= 0 || quantity > 100) {
            throw new BadRequestException("Quantity must be between 0 and 100");
        }


        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);

        productRepository.save(product);


        return new CreateProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity()
        );

    }

}
