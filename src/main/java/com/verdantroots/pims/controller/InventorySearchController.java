package com.verdantroots.pims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InventorySearchController {

    @GetMapping("/inventory-search")
    public String showInventorySearchPage() {
        return "inventory-search";
    }
}