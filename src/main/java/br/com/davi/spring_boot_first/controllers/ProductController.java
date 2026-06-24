package br.com.davi.spring_boot_first.controllers;

import br.com.davi.spring_boot_first.dto.request.CreateProductRequest;
import br.com.davi.spring_boot_first.dto.request.EditProductRequest;
import br.com.davi.spring_boot_first.dto.response.CreateProductResponse;
import br.com.davi.spring_boot_first.dto.response.EditProductResponse;
import br.com.davi.spring_boot_first.dto.response.ListProductResponse;
import br.com.davi.spring_boot_first.service.CreateProductService;
import br.com.davi.spring_boot_first.service.DeleteProductService;
import br.com.davi.spring_boot_first.service.EditProductService;
import br.com.davi.spring_boot_first.service.ListProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor


public class ProductController {

    private final CreateProductService createProductService;
    private final ListProductService listProductService;
    private final EditProductService editProductService;
    private final DeleteProductService deleteProductService;



    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public CreateProductResponse createProduct(
            @Valid @RequestBody CreateProductRequest createProductRequest
    )
    {
        return createProductService.createNewProduct(
            createProductRequest.getName(),
            createProductRequest.getPrice(),
            createProductRequest.getQuantity());
    }


    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public List<ListProductResponse> catchProducts(
         @RequestParam(defaultValue = "0") int page
    )
    {
        return listProductService.listProducts(page);
    }


    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public EditProductResponse changeProduct(
        @PathVariable Long id,
        @Valid @RequestBody EditProductRequest editProductRequest
    )
    {
        return editProductService.editProduct(
            id,
            editProductRequest.getName(),
            editProductRequest.getPrice(),
            editProductRequest.getQuantity());
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public Long removeProduct(
        @PathVariable Long id
    )
    {
        return deleteProductService.deleteProduct(id);
    }

}

