package com.verdantroots.pims.service;

import com.verdantroots.pims.entity.LocalGood;
import com.verdantroots.pims.repository.LocalGoodRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LocalGoodService {

    private final LocalGoodRepository localGoodRepository;

    public LocalGoodService(LocalGoodRepository localGoodRepository) {
        this.localGoodRepository = localGoodRepository;
    }

    public List<LocalGood> getAllLocalGoods() {
        return localGoodRepository.findAll();
    }

    public Optional<LocalGood> getLocalGoodById(Long id) {
        return localGoodRepository.findById(id);
    }

    public LocalGood saveLocalGood(LocalGood localGood) {
        return localGoodRepository.save(localGood);
    }

    public void deleteLocalGoodById(Long id) {
        localGoodRepository.deleteById(id);
    }

}
