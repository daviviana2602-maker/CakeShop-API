package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.ProductEntity;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.ConflictException;
import br.com.davi.spring_boot_first.repository.ProductRepository;
import br.com.davi.spring_boot_first.dto.response.CreateProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

import static br.com.davi.spring_boot_first.normalization.StringNormalizer.normalizeName;


@Service
public class CreateProductService {

    private final ProductRepository productRepository;


    public CreateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    private boolean existsProductByName(String email) {
        return productRepository.existsByName(email);
    }


    @Transactional
    public CreateProductResponse createNewProduct(String name, BigDecimal price, Integer quantity) {


        name = normalizeName(name);


        if (name.length() < 3) {
            throw new BadRequestException("Name must be at least 3 characters");
        }


        if (price.compareTo(BigDecimal.valueOf(5000)) > 0 || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Price must be between 0 and 5000");
        }

        if (quantity <= 0 || quantity > 100) {
            throw new BadRequestException("Quantity must be between 0 and 100");
        }


        if (existsProductByName(name)) {
            throw new ConflictException("Product already exists");
        }
        

        ProductEntity product = new ProductEntity();

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
