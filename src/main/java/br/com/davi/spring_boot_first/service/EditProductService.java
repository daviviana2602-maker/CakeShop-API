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

import static br.com.davi.spring_boot_first.normalization.StringNormalizer.normalizeName;


@Service
public class EditProductService {


    private final ProductRepository productRepository;


    public EditProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    private ProductEntity findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }


    private boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }


    @Transactional
    public EditProductResponse editProduct(Long id, String name, BigDecimal price, Integer quantity) {


        if (name == null && price == null && quantity == null) {
            throw new BadRequestException("At least one field is required");
        }

        if (name != null) {

            name = normalizeName(name);

            if (name.length() < 3) {
                throw new BadRequestException("name must be at least 3 characters");
            }

        }


        ProductEntity product = findProductById(id);


        if (name != null) {

            if (existsByName(name)) {
                throw new ConflictException("Product with this name already exists");
            }

            product.setName(name);
        }


        if (quantity != null) {

            Integer newQuantity = product.getQuantity() + quantity;

            if (newQuantity < 0) {
                throw new BadRequestException("Product quantity needs to be between 0 and 100");
            }

            product.setQuantity(newQuantity);
        }


        if (price != null) {
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