package com.verdantroots.pims.controller;

import com.verdantroots.pims.entity.Plant;
import com.verdantroots.pims.service.PlantService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping
    public String savePlant(@ModelAttribute Plant plant) {
        plantService.savePlant(plant);
        return "redirect:/plants";
    }

    @GetMapping("/delete/{id}")
    public String deletePlant(@PathVariable Long id) {
        plantService.deletePlant(id);
        return "redirect:/plants";
    }

}
