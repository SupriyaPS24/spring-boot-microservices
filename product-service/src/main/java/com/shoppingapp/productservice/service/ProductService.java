package com.shoppingapp.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shoppingapp.productservice.dto.ProductRequest;
import com.shoppingapp.productservice.dto.ProductResponse;
import com.shoppingapp.productservice.model.Product;
import com.shoppingapp.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	private final ProductRepository productRepository;

	/*
	 * public ProductService(ProductRepository productRepository) {
	 * this.productRepository = productRepository; }
	 */
	public void createProduct(ProductRequest productReq) {
		Product product = Product.builder().name(productReq.getName()).description(productReq.getDescription())
				.price(productReq.getPrice()).build();

		productRepository.save(product);
		log.info("Product " + product.getId() + " is saved"); // is same as below, slf4j allows us to have place-holders
																// {}
		log.info("Product {} is saved", product.getId());

	}

	public List<ProductResponse> getAllProducts() {
		
		List <Product> products = productRepository.findAll();
		//in order to map products into productResponse class using streams, and then collect it into list
		return products.stream().map(this::mapToProductResponse).toList();
		
	}

	private ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder().id(product.getId()).name(product.getName())
				.description(product.getDescription()).price(product.getPrice()).build();
	}

}
