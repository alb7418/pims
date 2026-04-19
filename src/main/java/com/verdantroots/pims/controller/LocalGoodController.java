package com.verdantroots.pims.controller;

import com.verdantroots.pims.entity.LocalGood;
import com.verdantroots.pims.service.LocalGoodService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/local-goods")
public class LocalGoodController {

    private final LocalGoodService localGoodService;

    public LocalGoodController(LocalGoodService localGoodService) {
        this.localGoodService = localGoodService;
    }

    @GetMapping
    public String getAllLocalGoods(Model model) {
        List<LocalGood> localGoods = localGoodService.getAllLocalGoods();
        model.addAttribute("localGoods", localGoods);
        return "local-goods";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("localGood", new LocalGood());
        return "local-good-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        LocalGood localGood = localGoodService.getLocalGoodById(id).orElseThrow(()->new IllegalArgumentException("Invalid local good id: " + id));
        model.addAttribute("localGood", localGood);
        return "local-good-form";
    }

    @PostMapping
    public String saveLocalGood(@ModelAttribute("LocalGood") LocalGood localGood) {
        localGoodService.saveLocalGood(localGood);
        return "redirect:/local-goods";
    }

    @GetMapping("/delete/{id}")
    public String deleteLocalGood(@PathVariable Long id) {
        localGoodService.deleteLocalGoodById(id);
        return "redirect:/local-goods";
    }

}
