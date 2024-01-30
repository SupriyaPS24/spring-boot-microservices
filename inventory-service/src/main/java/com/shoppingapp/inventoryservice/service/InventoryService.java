package com.shoppingapp.inventoryservice.service;

import com.shoppingapp.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
//@Transactional
public class InventoryService {

    private final InventoryRepository inventoryRepo;

    @Transactional(readOnly = true)
    public boolean isInStock(List<String> skuCode) {
        return inventoryRepo.findBySkuCodeIn(skuCode).isPresent();
    }
}
