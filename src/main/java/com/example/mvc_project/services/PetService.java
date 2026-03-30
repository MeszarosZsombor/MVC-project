package com.example.mvc_project.services;

import com.example.mvc_project.domain.dto.PetDto;
import com.example.mvc_project.domain.entities.PetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PetService {
    PetEntity save(PetDto petDto);

    List<PetEntity> findAll();

    Page<PetEntity> findAll(Pageable pageable);

    Optional<PetEntity> findOne(Long id);

    boolean isExists(Long id);

    PetEntity partialUpdate(Long id, PetDto petDto);

    void delete(Long id);
}
