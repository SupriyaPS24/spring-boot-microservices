package com.shopping.orderservice.service;

import java.util.Arrays;
import java.util.UUID;
import java.util.List;

import com.shopping.orderservice.dto.InventoryResponse;
import org.springframework.stereotype.Service;

import com.shopping.orderservice.dto.OrderLineItemsDto;
import com.shopping.orderservice.dto.OrderServiceRequest;
import com.shopping.orderservice.model.Order;
import com.shopping.orderservice.model.OrderLineItems;
import com.shopping.orderservice.repository.OrderServiceRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
	
	private final OrderServiceRepository orderRepo;
	private final WebClient webClient;
	public void placeOrder(OrderServiceRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemDtoList().stream().map(this::mapToDto)
				.toList();
		 
		order.setOrderLineItemsList(orderLineItems);
		List<String> skuCodes = order.getOrderLineItemsList().stream().
				map(OrderLineItems::getSkuCode).toList();

		InventoryResponse[] inventoryResponseArray = webClient.get().uri("http//localhost:8082/api/inventory",
						uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build()).
				retrieve().bodyToMono(InventoryResponse[].class).block();

		boolean allProductsInStock = Arrays.stream(inventoryResponseArray).
				allMatch(InventoryResponse::getIsInStock);

		if(allProductsInStock) {
			orderRepo.save(order);
		}
		else{
		throw new IllegalArgumentException("Product is not in stock, please try again later");
		}
		//orderRepo.save(order);
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;

	}

}
