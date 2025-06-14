package com.examples.spring_jpa.examples;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.examples.spring_jpa.examples.product.Product;
import com.examples.spring_jpa.examples.product.ProductRepository;

@SpringBootApplication
public class ExamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamplesApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ProductRepository productRepository) {
		return args -> {
			Product product = new Product();
			product.setName("Sample Product");
			product.setDescription("This is a sample product description.");
			product.setPrice(new BigDecimal("19.99"));
			product.setId(UUID.randomUUID());
			product.setStockLevel(100);
			productRepository.save(product);
		};
	}

}
