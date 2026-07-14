package br.com.davi.spring_boot_first.seeders;


import br.com.davi.spring_boot_first.entity.ProductEntity;
import br.com.davi.spring_boot_first.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
public class ProductsSeeder implements CommandLineRunner {


    private final ProductRepository productRepository;


    public ProductsSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {

        if (productRepository.count() > 0) {
            return;
        }


        List<ProductEntity> products = List.of(
                ProductEntity.builder()
                        .name("Chocolate Cake")
                        .price(new BigDecimal("49.90"))
                        .build(),

                ProductEntity.builder()
                        .name("Strawberry Cake")
                        .price(new BigDecimal("54.90"))
                        .build(),

                ProductEntity.builder()
                        .name("Red Velvet Cake")
                        .price(new BigDecimal("69.90"))
                        .build(),

                ProductEntity.builder()
                        .name("Lemon Cake")
                        .price(new BigDecimal("42.50"))
                        .build(),

                ProductEntity.builder()
                        .name("Carrot Cake")
                        .price(new BigDecimal("39.90"))
                        .build(),

                ProductEntity.builder()
                        .name("Cheesecake")
                        .price(new BigDecimal("59.90"))
                        .build(),

                ProductEntity.builder()
                        .name("Chocolate Cupcake")
                        .price(new BigDecimal("9.90"))
                        .build(),

                ProductEntity.builder()
                        .name("Vanilla Cupcake")
                        .price(new BigDecimal("8.90"))
                        .build(),

                ProductEntity.builder()
                        .name("Brownie")
                        .price(new BigDecimal("12.90"))
                        .build(),

                ProductEntity.builder()
                        .name("Brigadeiro")
                        .price(new BigDecimal("4.50"))
                        .build(),

                ProductEntity.builder()
                        .name("Beijinho")
                        .price(new BigDecimal("4.50"))
                        .build(),

                ProductEntity.builder()
                        .name("Chocolate Donut")
                        .price(new BigDecimal("11.90"))
                        .build(),

                ProductEntity.builder()
                        .name("Apple Pie")
                        .price(new BigDecimal("32.90"))
                        .build(),

                ProductEntity.builder()
                        .name("Banoffee Pie")
                        .price(new BigDecimal("37.90"))
                        .build(),

                ProductEntity.builder()
                        .name("Cookie Chocolate Chip")
                        .price(new BigDecimal("7.90"))
                        .build(),

                ProductEntity.builder()
                        .name("Macaron Pistachio")
                        .price(new BigDecimal("8.90"))
                        .build(),

                ProductEntity.builder()
                        .name("Cinnamon Roll")
                        .price(new BigDecimal("15.90"))
                        .build(),

                ProductEntity.builder()
                        .name("Croissant")
                        .price(new BigDecimal("13.90"))
                        .build(),

                ProductEntity.builder()
                        .name("Blueberry Muffin")
                        .price(new BigDecimal("12.50"))
                        .build(),

                ProductEntity.builder()
                        .name("Tiramisu")
                        .price(new BigDecimal("29.90"))
                        .build()
        );

        productRepository.saveAll(products);

        System.out.println("Products seeded successfully!");
    }
}
