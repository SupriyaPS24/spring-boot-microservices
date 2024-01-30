package com.shoppingapp.inventoryservice;

import com.shoppingapp.inventoryservice.model.Inventory;
import com.shoppingapp.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepo){
		 return args -> {
			 Inventory inventory = new Inventory();
			 inventory.setSkuCode("wallet");
			 inventory.setQuantity(100);

			 Inventory inventory1 = new Inventory();
			 inventory1.setSkuCode("iPhone");
			 inventory1.setQuantity(0);

			 inventoryRepo.save(inventory);
			 inventoryRepo.save(inventory1);
		 };
	}

}
