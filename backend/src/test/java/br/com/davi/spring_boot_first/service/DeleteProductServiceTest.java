package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.ProductEntity;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class DeleteProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProductService deleteProductService;


    @Test
    void shouldDeleteProductSuccessfully() {

        ProductEntity product = new ProductEntity();
        product.setId(1L);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));


        Long response =
                deleteProductService.deleteProduct(1L);

        assertEquals(1L, response);

        verify(productRepository)
                .delete(product);

    }

    @Test
    void shouldDeleteProductThatDoesNotExist() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());


        assertThrows(
                NotFoundException.class,
                () -> deleteProductService.deleteProduct(
                1L
            )
        );

    }

}