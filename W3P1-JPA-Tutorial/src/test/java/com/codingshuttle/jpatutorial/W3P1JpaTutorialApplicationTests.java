package com.codingshuttle.jpatutorial;

import com.codingshuttle.jpatutorial.entities.ProductEntity;
import com.codingshuttle.jpatutorial.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class W3P1JpaTutorialApplicationTests {

	@Autowired
	ProductRepository productRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testRepository(){
		ProductEntity productEntity = ProductEntity.builder()
				.sku("Nestle123")
				.title("Nestle Choclate")
				.price(BigDecimal.valueOf(123.45))
				.quantity(12)
				.build();

		ProductEntity savedProductEntity = productRepository.save(productEntity);
		System.out.println(savedProductEntity);
	}

	@Test
	void getRepository() {
//		List<ProductEntity> entities = productRepository.findAll();
//		List<ProductEntity> entities = productRepository.findByTitle("Pepsi");
//		List<ProductEntity> entities = productRepository.findByCreatedAtAfter(
//				LocalDateTime.of(2024, 1, 1, 0, 0, 0 ));
//		List<ProductEntity> entities = productRepository.findByQuantityGreaterThanOrPriceLessThan(4, BigDecimal.valueOf(23.45));
		List<ProductEntity> entities = productRepository.findByTitleContainingIgnoreCase("PEpsi");
		System.out.println(entities);
	}

	@Test
	void getSingleFromRepository() {
		Optional<ProductEntity> productEntity = productRepository
				.findByTitleAndPrice("Pepsi", BigDecimal.valueOf(14.4));
		productEntity.ifPresent(System.out::println);
	}

}
