package com.shoppingapp.productservice;


import java.math.BigDecimal;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingapp.productservice.dto.ProductRequest;
import com.shoppingapp.productservice.repository.ProductRepository;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

	@Autowired
	private MockMvc mockmvc;

	@Autowired
	private ObjectMapper objMapper;
	
	@Autowired
	private ProductRepository prodRepo;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("soring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest prodReq = getProdRequest();
		String prodRequestString = objMapper.writeValueAsString(prodReq);
		
		mockmvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(prodRequestString))
		.andExpect((ResultMatcher) MockMvcResultMatchers.status().isCreated());
		System.out.println(prodRepo.findAll().size());
		Assertions.assertTrue(prodRepo.findAll().size() == 3);
		// content method will only take string param, so we need to convert prodReq
		// object to string therefore ObjectMpper
		// what does ObjMapper do? -> converts json to POJO obj and POJO to JSON
	}

	private ProductRequest getProdRequest() {
		return ProductRequest.builder().name("wallet").description("this is a wallet").price(BigDecimal.valueOf(200))
				.build();
	}

}
