package com.verdantroots.pims.controller;

import com.verdantroots.pims.service.*;
import com.verdantroots.pims.entity.LocalGood;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.time.LocalDate;

@Controller
public class HomeController {

    private final PlantService plantService;
    private final LocalGoodService localGoodService;
    private final SupplyService supplyService;

    public HomeController(PlantService plantService, LocalGoodService localGoodService, SupplyService supplyService) {
        this.plantService = plantService;
        this.localGoodService = localGoodService;
        this.supplyService = supplyService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/")
    public String home(Model model) {
        int plantCount = plantService.getAllPlants().size();
        int localGoodsCount = localGoodService.getAllLocalGoods().size();
        int supplyCount = supplyService.getAllSupplies().size();

        int lowStockCount = (int) plantService.getAllPlants().stream()
                .filter(p -> p.getQuantityInStock() != null
                        && p.getReorderLevel() != null
                        && p.getQuantityInStock() <= p.getReorderLevel())
                .count();

        lowStockCount += (int) supplyService.getAllSupplies().stream()
                .filter(s -> s.getQuantityInStock() != null
                        && s.getReorderLevel() != null
                        && s.getQuantityInStock() <= s.getReorderLevel())
                .count();

        lowStockCount += (int) localGoodService.getAllLocalGoods().stream()
                .filter(l -> l.getQuantityInStock() != null
                        && l.getReorderLevel() != null
                        && l.getQuantityInStock() <= l.getReorderLevel())
                .count();

        List<LocalGood> localGoods = localGoodService.getAllLocalGoods();

        int expiringCount = (int) localGoods.stream()
                .filter(l -> l.getExpirationDate() != null
                        && l.getExpirationDate().isBefore(LocalDate.now().plusDays(7)))
                .count();

        model.addAttribute("plantCount", plantCount);
        model.addAttribute("supplyCount", supplyCount);
        model.addAttribute("lowStockCount", lowStockCount);
        model.addAttribute("expiringCount", expiringCount);
        model.addAttribute("localGoodsCount", localGoodsCount);

        return "index";
    }

}
