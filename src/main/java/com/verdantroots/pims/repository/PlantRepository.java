package com.verdantroots.pims.repository;

import com.verdantroots.pims.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findByCommonNameContainingIgnoreCase(String commonName);
}
