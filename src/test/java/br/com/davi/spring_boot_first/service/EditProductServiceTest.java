package br.com.davi.spring_boot_first.service;


import br.com.davi.spring_boot_first.dto.response.EditProductResponse;
import br.com.davi.spring_boot_first.entity.ProductEntity;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.ConflictException;
import br.com.davi.spring_boot_first.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EditProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private EditProductService editProductService;


    @Test
    public void shouldEditProductSuccessfully() {

        ProductEntity product = new ProductEntity();
        product.setId(1L);
        product.setName("OldName");
        product.setPrice(BigDecimal.valueOf(10));
        product.setQuantity(5);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));


        EditProductResponse response =
                editProductService.editProduct(
                        1L,
                        "TestProduct",
                        BigDecimal.valueOf(50),
                        10
                );

        assertEquals("TestProduct", response.getName());
        assertEquals(0, BigDecimal.valueOf(50).compareTo(response.getPrice()));
        assertEquals(15, response.getQuantity());

    }


    @Test
    public void shouldWrongProductWithEmptyName() {

        ProductEntity product = new ProductEntity();
        product.setId(1L);
        product.setName("OldName");
        product.setPrice(BigDecimal.valueOf(10));
        product.setQuantity(5);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));


        assertThrows(
                BadRequestException.class,
                () -> editProductService.editProduct(
                        1L,
                        "",
                        BigDecimal.valueOf(50),
                        10
                )
        );

    }

    @Test
    void shouldWrongProductNameAlreadyExists() {

        ProductEntity product = new ProductEntity();
        product.setId(1L);
        product.setName("TestProduct");

        ProductEntity anotherProduct = new ProductEntity();
        anotherProduct.setId(2L);
        anotherProduct.setName("TestProductTwo");


        when(productRepository.findAll())
                .thenReturn(List.of(anotherProduct));

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));


        assertThrows(
                ConflictException.class,
                () -> editProductService.editProduct(
                        1L,
                        "TestProductTwo",
                        null,
                        null
                )
        );

    }


    @Test
    void shouldWrongWhenPriceIsNegative() {

        ProductEntity product = new ProductEntity();
        product.setId(1L);
        product.setPrice(BigDecimal.valueOf(40));


        when(productRepository.findAll())
                .thenReturn(List.of());

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));


        assertThrows(
                BadRequestException.class,
                () -> editProductService.editProduct(
                        1L,
                        null,
                        BigDecimal.valueOf(-10),
                        null
                )
        );

    }


    @Test
    void shouldWrongWhenQuantityIsZero() {

        ProductEntity product = new ProductEntity();
        product.setId(1L);
        product.setQuantity(5);

        when(productRepository.findAll())
                .thenReturn(List.of());

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));


        assertThrows(
                BadRequestException.class,
                () -> editProductService.editProduct(
                        1L,
                        null,
                        null,
                        -10
                )
        );

    }

}
