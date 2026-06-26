package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.ProductEntity;
import br.com.davi.spring_boot_first.repository.ProductRepository;
import br.com.davi.spring_boot_first.dto.response.ListProductResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ListProductService {

    private final ProductRepository productRepository;


    public ListProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Cacheable(value = "products", key = "#page")
    public List<ListProductResponse> listProducts(int page) {

        Pageable pageable = PageRequest.of(page, 10);

        Page<ProductEntity> pageProducts = productRepository.findAll(pageable);


        return pageProducts.stream()
            .map(product -> new ListProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice()
            ))
            .toList();

        }

}