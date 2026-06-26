package br.com.davi.spring_boot_first.service;


import br.com.davi.spring_boot_first.dto.response.CreateProductResponse;
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


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class CreateProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductService createProductService;


    @Test
    void shouldCreateProductSuccessfully() {

        when(productRepository.existsByName("Notebook"))
                .thenReturn(false);


        CreateProductResponse response =
                createProductService.createNewProduct(
                        "Notebook",
                        BigDecimal.valueOf(2500)
                );


        assertEquals("Notebook", response.getName());
        assertEquals(BigDecimal.valueOf(2500), response.getPrice());


        verify(productRepository)
                .save(any(ProductEntity.class));

    }


    @Test
    void shouldBadRequestWhenNameIsBlank() {

        assertThrows(
            BadRequestException.class,
            () -> createProductService.createNewProduct(
                   "",
                   BigDecimal.valueOf(100)
                )
        );


        verify(productRepository, never())
                .save(any());

    }


    @Test
    void shouldThrowConflictWhenProductNameAlreadyExists() {

        when(productRepository.existsByName("Notebook"))
                .thenReturn(true);

        assertThrows(
                ConflictException.class,
                () -> createProductService.createNewProduct(
                        "Notebook",
                        BigDecimal.valueOf(1000)
                )
        );

        verify(productRepository, never())
                .save(any());

    }


    @Test
    void shouldBadRequestWhenPriceIsNegative() {

        assertThrows(
                BadRequestException.class,
                () -> createProductService.createNewProduct(
                        "TestProduct",
                        BigDecimal.valueOf(-10)
                )
        );


        verify(productRepository, never())
                .save(any());

    }

}