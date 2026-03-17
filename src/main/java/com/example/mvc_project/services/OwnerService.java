package com.example.mvc_project.services;

import com.example.mvc_project.domain.entities.OwnerEntity;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    OwnerEntity save(OwnerEntity ownerEntity);

    List<OwnerEntity> findAll();

    Optional<OwnerEntity> findOne(Long id);

    boolean isExists(Long id);
}
