package com.verdantroots.pims.controller;

import com.verdantroots.pims.entity.LocalGood;
import com.verdantroots.pims.entity.Plant;
import com.verdantroots.pims.service.LocalGoodService;
import com.verdantroots.pims.service.PlantService;
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
    private final PlantService plantService;

    public InventorySearchController(LocalGoodService localGoodService, PlantService plantService) {
        this.localGoodService = localGoodService;
        this.plantService = plantService;
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
            List<LocalGood> results = List.of();
            List<Plant> plantResults = List.of();

            // PLANT ONLY
            if ("plant".equals(inventoryType)) {
                plantResults = plantService.getAllPlants();

                if (perishable != null && perishable) {
                    plantResults = List.of();
                } else {
                    if (name != null && !name.isBlank()) {
                        plantResults = plantResults.stream()
                                .filter(item -> item.getCommonName() != null &&
                                        item.getCommonName().toLowerCase().contains(name.toLowerCase()))
                                .collect(Collectors.toList());
                    }

                    if (category != null && !category.isBlank()) {
                        plantResults = plantResults.stream()
                                .filter(item -> item.getCategory() != null &&
                                        item.getCategory().toLowerCase().contains(category.toLowerCase()))
                                .collect(Collectors.toList());
                    }

                    if (vendorOrSupplier != null && !vendorOrSupplier.isBlank()) {
                        plantResults = plantResults.stream()
                                .filter(item -> item.getSupplier() != null &&
                                        item.getSupplier().toLowerCase().contains(vendorOrSupplier.toLowerCase()))
                                .collect(Collectors.toList());
                    }

                    if (location != null && !location.isBlank()) {
                        plantResults = plantResults.stream()
                                .filter(item -> item.getLocation() != null &&
                                        item.getLocation().toLowerCase().contains(location.toLowerCase()))
                                .collect(Collectors.toList());
                    }

                    if (lowStock != null && lowStock) {
                        plantResults = plantResults.stream()
                                .filter(item -> item.getQuantityInStock() != null
                                        && item.getReorderLevel() != null
                                        && item.getQuantityInStock() <= item.getReorderLevel())
                                .collect(Collectors.toList());
                    }
                }
            }

            // LOCAL GOODS ONLY
            else if ("localGood".equals(inventoryType)) {
                results = localGoodService.getAllLocalGoods();

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

            // ALL
            else if (inventoryType == null || inventoryType.isBlank()) {
                results = localGoodService.getAllLocalGoods();
                plantResults = plantService.getAllPlants();

                if (name != null && !name.isBlank()) {
                    results = results.stream()
                            .filter(item -> item.getName() != null &&
                                    item.getName().toLowerCase().contains(name.toLowerCase()))
                            .collect(Collectors.toList());

                    plantResults = plantResults.stream()
                            .filter(item -> item.getCommonName() != null &&
                                    item.getCommonName().toLowerCase().contains(name.toLowerCase()))
                            .collect(Collectors.toList());
                }

                if (category != null && !category.isBlank()) {
                    results = results.stream()
                            .filter(item -> item.getCategory() != null &&
                                    item.getCategory().toLowerCase().contains(category.toLowerCase()))
                            .collect(Collectors.toList());

                    plantResults = plantResults.stream()
                            .filter(item -> item.getCategory() != null &&
                                    item.getCategory().toLowerCase().contains(category.toLowerCase()))
                            .collect(Collectors.toList());
                }

                if (vendorOrSupplier != null && !vendorOrSupplier.isBlank()) {
                    results = results.stream()
                            .filter(item -> item.getLocalVendor() != null &&
                                    item.getLocalVendor().toLowerCase().contains(vendorOrSupplier.toLowerCase()))
                            .collect(Collectors.toList());

                    plantResults = plantResults.stream()
                            .filter(item -> item.getSupplier() != null &&
                                    item.getSupplier().toLowerCase().contains(vendorOrSupplier.toLowerCase()))
                            .collect(Collectors.toList());
                }

                if (location != null && !location.isBlank()) {
                    results = results.stream()
                            .filter(item -> item.getLocation() != null &&
                                    item.getLocation().toLowerCase().contains(location.toLowerCase()))
                            .collect(Collectors.toList());

                    plantResults = plantResults.stream()
                            .filter(item -> item.getLocation() != null &&
                                    item.getLocation().toLowerCase().contains(location.toLowerCase()))
                            .collect(Collectors.toList());
                }

                if (perishable != null && perishable) {
                    results = results.stream()
                            .filter(LocalGood::isPerishable)
                            .collect(Collectors.toList());

                    plantResults = List.of();
                }

                if (lowStock != null && lowStock) {
                    results = results.stream()
                            .filter(item -> item.getQuantityInStock() != null
                                    && item.getReorderLevel() != null
                                    && item.getQuantityInStock() <= item.getReorderLevel())
                            .collect(Collectors.toList());

                    plantResults = plantResults.stream()
                            .filter(item -> item.getQuantityInStock() != null
                                    && item.getReorderLevel() != null
                                    && item.getQuantityInStock() <= item.getReorderLevel())
                            .collect(Collectors.toList());
                }
            }

            model.addAttribute("results", results);
            model.addAttribute("plantResults", plantResults);
        }

        return "inventory-search";
    }
}