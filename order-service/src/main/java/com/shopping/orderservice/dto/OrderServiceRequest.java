package com.shopping.orderservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderServiceRequest {

	private List<OrderLineItemsDto> orderLineItemDtoList;
	
}
