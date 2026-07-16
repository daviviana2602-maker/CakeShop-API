package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.ProductEntity;
import br.com.davi.spring_boot_first.enums.ErrorCodeEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.ConflictException;
import br.com.davi.spring_boot_first.repository.ProductRepository;
import br.com.davi.spring_boot_first.dto.response.CreateProductResponse;
import org.springframework.cache.annotation.CacheEvict;
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
    @CacheEvict(value = "products", allEntries = true)
    public CreateProductResponse createNewProduct(String name, BigDecimal price) {


        name = normalizeName(name);


        if (name.length() < 3) {
            throw new BadRequestException(ErrorCodeEnum.INVALID_NAME,"Name must be at least 3 characters");
        }


        if (price.compareTo(BigDecimal.valueOf(5000)) > 0 || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException(ErrorCodeEnum.PRODUCT_INVALID_PRICE, "Price must be between 0 and 5000");
        }


        if (existsProductByName(name)) {
            throw new ConflictException(ErrorCodeEnum.PRODUCT_ALREADY_EXISTS, "Product already exists");
        }
        

        ProductEntity product = new ProductEntity();

        product.setName(name);
        product.setPrice(price);

        productRepository.save(product);


        return new CreateProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice()
        );

    }

}
