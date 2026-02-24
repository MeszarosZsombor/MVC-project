package com.example.mvc_project.dao;

import com.example.mvc_project.domain.Owner;

import java.util.Optional;

public interface OwnerDao {
    void create(Owner owner);

    Optional<Owner> findOne(long l);
}
