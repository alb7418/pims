package com.verdantroots.pims.repository;

import com.verdantroots.pims.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplyRepository extends JpaRepository<Supply, Long> {

    List<Supply> findByItemNameContainingIgnoreCase(String itemName);

}