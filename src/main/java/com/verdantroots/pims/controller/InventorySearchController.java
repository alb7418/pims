package com.verdantroots.pims.controller;

import com.verdantroots.pims.entity.LocalGood;
import com.verdantroots.pims.service.LocalGoodService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class InventorySearchController {

    private final LocalGoodService localGoodService;

    public InventorySearchController(LocalGoodService localGoodService) {
        this.localGoodService = localGoodService;
    }

    @GetMapping("/inventory-search")
    public String searchInventory(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String inventoryType,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String vendorOrSupplier,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Boolean perishable,
            @RequestParam(required = false) Boolean lowStock,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            Model model) {

        boolean hasSearch = (name != null && !name.isBlank())
                || (inventoryType != null && !inventoryType.isBlank())
                || (category != null && !category.isBlank())
                || (vendorOrSupplier != null && !vendorOrSupplier.isBlank())
                || (location != null && !location.isBlank())
                || perishable != null
                || lowStock != null
                || minPrice != null
                || maxPrice != null;

        if (hasSearch) {

            List<LocalGood> results = localGoodService.getAllLocalGoods();

            if (inventoryType != null && !inventoryType.isBlank() && !inventoryType.equals("localGood")) {
                results = List.of(); // empty list for now
            }

            if (!results.isEmpty()) {

                if (name != null && !name.isBlank()) {
                    results = results.stream()
                            .filter(item -> item.getName() != null &&
                                    item.getName().toLowerCase().contains(name.toLowerCase()))
                            .collect(Collectors.toList());
                }

                if (category != null && !category.isBlank()) {
                    results = results.stream()
                            .filter(item -> item.getCategory() != null &&
                                    item.getCategory().toLowerCase().contains(category.toLowerCase()))
                            .collect(Collectors.toList());
                }

                if (vendorOrSupplier != null && !vendorOrSupplier.isBlank()) {
                    results = results.stream()
                            .filter(item -> item.getLocalVendor() != null &&
                                    item.getLocalVendor().toLowerCase().contains(vendorOrSupplier.toLowerCase()))
                            .collect(Collectors.toList());
                }

                if (location != null && !location.isBlank()) {
                    results = results.stream()
                            .filter(item -> item.getLocation() != null &&
                                    item.getLocation().toLowerCase().contains(location.toLowerCase()))
                            .collect(Collectors.toList());
                }

                if (perishable != null && perishable) {
                    results = results.stream()
                            .filter(LocalGood::isPerishable)
                            .collect(Collectors.toList());
                }

                if (lowStock != null && lowStock) {
                    results = results.stream()
                            .filter(item -> item.getQuantityInStock() != null
                                    && item.getReorderLevel() != null
                                    && item.getQuantityInStock() <= item.getReorderLevel())
                            .collect(Collectors.toList());
                }
            }

            model.addAttribute("results", results);
        }

        return "inventory-search";
    }
}