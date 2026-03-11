package com.example.mvc_project.repositories;

import com.example.mvc_project.domain.entities.OwnerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends CrudRepository<OwnerEntity, Long> {
    Iterable<OwnerEntity> roleIs(String role);
}
