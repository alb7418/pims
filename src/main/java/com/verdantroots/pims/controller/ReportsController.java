package com.verdantroots.pims.controller;

import com.verdantroots.pims.entity.InventoryRow;
import com.verdantroots.pims.entity.LocalGood;
import com.verdantroots.pims.entity.Plant;
import com.verdantroots.pims.entity.Supply;
import com.verdantroots.pims.service.LocalGoodService;
import com.verdantroots.pims.service.PlantService;
import com.verdantroots.pims.service.SupplyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReportsController {

    private final PlantService plantService;
    private final SupplyService supplyService;
    private final LocalGoodService localGoodService;

    public ReportsController(PlantService plantService,
                             SupplyService supplyService,
                             LocalGoodService localGoodService) {
        this.plantService = plantService;
        this.supplyService = supplyService;
        this.localGoodService = localGoodService;
    }

    @GetMapping("/reports")
    public String reports() {
        return "reports";
    }

    @GetMapping("/reports/low-stock")
    public String lowStockReport(Model model) {

        List<Plant> lowStockPlants = plantService.getAllPlants().stream()
                .filter(item -> item.getQuantityInStock() != null
                        && item.getReorderLevel() != null
                        && item.getQuantityInStock() <= item.getReorderLevel())
                .collect(Collectors.toList());

        List<Supply> lowStockSupplies = supplyService.getAllSupplies().stream()
                .filter(item -> item.getQuantityInStock() != null
                        && item.getReorderLevel() != null
                        && item.getQuantityInStock() <= item.getReorderLevel())
                .collect(Collectors.toList());

        List<LocalGood> lowStockLocalGoods = localGoodService.getAllLocalGoods().stream()
                .filter(item -> item.getQuantityInStock() != null
                        && item.getReorderLevel() != null
                        && item.getQuantityInStock() <= item.getReorderLevel())
                .collect(Collectors.toList());

        model.addAttribute("lowStockPlants", lowStockPlants);
        model.addAttribute("lowStockSupplies", lowStockSupplies);
        model.addAttribute("lowStockLocalGoods", lowStockLocalGoods);

        return "low-stock-report";
    }

    @GetMapping("/reports/expiring")
    public String expiringReport(Model model) {

        List<LocalGood> expiringSoonLocalGoods = localGoodService.getAllLocalGoods().stream()
                .filter(item -> item.getExpirationDate() != null
                        && !item.getExpirationDate().isBefore(LocalDate.now())
                        && !item.getExpirationDate().isAfter(LocalDate.now().plusDays(7)))
                .collect(Collectors.toList());

        model.addAttribute("expiringSoonLocalGoods", expiringSoonLocalGoods);

        return "expiring-report";
    }

    @GetMapping("/reports/inventory-by-location")
    public String inventoryByLocationReport(Model model) {

        List<InventoryRow> rows = new ArrayList<>();

        for (Plant plant : plantService.getAllPlants()) {
            rows.add(new InventoryRow(
                    plant.getLocation(),
                    "Plant",
                    plant.getCommonName(),
                    plant.getQuantityInStock(),
                    plant.getReorderLevel(),
                    plant.getLastUpdated()
            ));
        }

        for (Supply supply : supplyService.getAllSupplies()) {
            rows.add(new InventoryRow(
                    supply.getLocation(),
                    "Supply",
                    supply.getItemName(),
                    supply.getQuantityInStock(),
                    supply.getReorderLevel(),
                    supply.getLastUpdated()
            ));
        }

        for (LocalGood localGood : localGoodService.getAllLocalGoods()) {
            rows.add(new InventoryRow(
                    localGood.getLocation(),
                    "Local Good",
                    localGood.getName(),
                    localGood.getQuantityInStock(),
                    localGood.getReorderLevel(),
                    localGood.getLastUpdated()
            ));
        }

        rows.sort((a, b) -> {
            String locA = a.getLocation();
            String locB = b.getLocation();

            boolean isAisleA = locA != null && locA.startsWith("Aisle ");
            boolean isAisleB = locB != null && locB.startsWith("Aisle ");

            if (isAisleA && !isAisleB) return -1;
            if (!isAisleA && isAisleB) return 1;

            if (isAisleA && isAisleB) {
                int numA = Integer.parseInt(locA.replace("Aisle ", ""));
                int numB = Integer.parseInt(locB.replace("Aisle ", ""));
                return Integer.compare(numA, numB);
            }

            if (locA == null) return 1;
            if (locB == null) return -1;

            return locA.compareToIgnoreCase(locB);
        });

        model.addAttribute("rows", rows);

        return "inventory-by-location-report";
    }

    @GetMapping("/reports/recently-updated")
    public String recentlyUpdatedReport(Model model) {

        List<InventoryRow> rows = new ArrayList<>();

        for (Plant plant : plantService.getAllPlants()) {
            rows.add(new InventoryRow(
                    plant.getLocation(),
                    "Plant",
                    plant.getCommonName(),
                    plant.getQuantityInStock(),
                    plant.getReorderLevel(),
                    plant.getLastUpdated()
            ));
        }

        for (Supply supply : supplyService.getAllSupplies()) {
            rows.add(new InventoryRow(
                    supply.getLocation(),
                    "Supply",
                    supply.getItemName(),
                    supply.getQuantityInStock(),
                    supply.getReorderLevel(),
                    supply.getLastUpdated()
            ));
        }

        for (LocalGood localGood : localGoodService.getAllLocalGoods()) {
            rows.add(new InventoryRow(
                    localGood.getLocation(),
                    "Local Good",
                    localGood.getName(),
                    localGood.getQuantityInStock(),
                    localGood.getReorderLevel(),
                    localGood.getLastUpdated()
            ));
        }

        rows.sort((a, b) -> {
            if (a.getLastUpdated() == null && b.getLastUpdated() == null) return 0;
            if (a.getLastUpdated() == null) return 1;
            if (b.getLastUpdated() == null) return -1;

            return b.getLastUpdated().compareTo(a.getLastUpdated());
        });

        model.addAttribute("rows", rows);

        return "recently-updated-report";
    }
}