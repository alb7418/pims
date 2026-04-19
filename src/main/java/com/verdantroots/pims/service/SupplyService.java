package com.verdantroots.pims.service;

import com.verdantroots.pims.entity.Supply;
import com.verdantroots.pims.repository.SupplyRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SupplyService {

    private final SupplyRepository supplyRepository;

    public SupplyService(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    public List<Supply> getAllSupplies() {
        return supplyRepository.findAll();
    }

    public Optional<Supply> getSupplyById(Long id) {
        return supplyRepository.findById(id);
    }

    public Supply saveSupply(Supply supply) {
        return supplyRepository.save(supply);
    }

    public void deleteSupplyById(Long id) {
        supplyRepository.deleteById(id);
    }

    public List<Supply> searchSuppliesByItemName(String itemName) {
        return supplyRepository.findByItemNameContainingIgnoreCase(itemName);
    }

}
