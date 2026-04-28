package com.verdantroots.pims.controller;

import com.verdantroots.pims.entity.Supply;
import com.verdantroots.pims.service.SupplyService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/supplies")
public class SupplyController {

    private final SupplyService supplyService;

    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @GetMapping
    public String getAllSupply(Model model) {
        List<Supply> supplies = supplyService.getAllSupplies();
        model.addAttribute("supplies", supplies);
        return "supplies";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("supply", new Supply());
        return "supply-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Supply supply = supplyService.getSupplyById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid supply id: " + id));
        model.addAttribute("supply", supply);
        return "supply-form";
    }

    @PostMapping
    public String saveSupply(@Valid @ModelAttribute("supply") Supply supply,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            return "supply-form";
        }

        supplyService.saveSupply(supply);
        return "redirect:/supplies";
    }

    @GetMapping("/delete/{id}")
    public String deleteSupply(@PathVariable Long id) {
        supplyService.deleteSupplyById(id);
        return "redirect:/supplies";
    }
}