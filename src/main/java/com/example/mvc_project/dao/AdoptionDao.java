package com.example.mvc_project.dao;

import com.example.mvc_project.domain.Adoption;

import java.util.List;
import java.util.Optional;

public interface AdoptionDao {

    void create(Adoption adoption);

    Optional<Adoption> findOne(long adoptionId);

    List<Adoption> find();
}
