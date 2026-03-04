package com.example.mvc_project.dao;

import com.example.mvc_project.domain.Pet;

import java.util.List;
import java.util.Optional;

public interface PetDao {

    void create(Pet pet);

    Optional<Pet> findOne(long l);

    List<Pet> find();

    void update(Long petId, Pet pet);
}
