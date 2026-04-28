package com.verdantroots.pims.controller;

import com.verdantroots.pims.entity.Plant;
import com.verdantroots.pims.service.PlantService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/plants")
public class PlantController {

    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping
    public String getAllPlants(Model model) {
        List<Plant> plants = plantService.getAllPlants();
        model.addAttribute("plants", plants);
        return "plants";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("plant", new Plant());
        return "plant-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Plant plant = plantService.getPlantById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid plant id " + id));
        model.addAttribute("plant", plant);
        return "plant-form";
    }

    @PostMapping
    public String savePlant(@Valid @ModelAttribute("plant") Plant plant,
                            BindingResult bindingResult,
                            Model model) {

        if (bindingResult.hasErrors()) {
            return "plant-form";
        }

        plantService.savePlant(plant);
        return "redirect:/plants";
    }

    @GetMapping("/delete/{id}")
    public String deletePlant(@PathVariable Long id) {
        plantService.deletePlant(id);
        return "redirect:/plants";
    }
}