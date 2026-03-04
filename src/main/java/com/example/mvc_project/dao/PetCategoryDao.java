package com.example.mvc_project.dao;

import com.example.mvc_project.domain.PetCategory;

import java.util.List;
import java.util.Optional;

public interface PetCategoryDao {

    void create(PetCategory petCategory);

    Optional<PetCategory> findOne(long petCategoryId);

    List<PetCategory> find();

    void update(Long petCategoryId, PetCategory petCategory);
}
