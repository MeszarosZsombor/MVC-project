package com.example.mvc_project.services;

import com.example.mvc_project.domain.entities.OwnerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    OwnerEntity save(OwnerEntity ownerEntity);

    List<OwnerEntity> findAll();

    Page<OwnerEntity> findAll(Pageable pageable);

    Optional<OwnerEntity> findOne(Long id);

    boolean isExists(Long id);

    OwnerEntity partialUpdate(Long id, OwnerEntity ownerEntity);

    void delete(Long id);
}
