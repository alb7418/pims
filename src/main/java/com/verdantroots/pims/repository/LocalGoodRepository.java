package com.verdantroots.pims.repository;

import com.verdantroots.pims.entity.LocalGood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalGoodRepository extends JpaRepository<LocalGood, Long> {
}