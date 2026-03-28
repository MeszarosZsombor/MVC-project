package com.example.mvc_project.repositories;

import com.example.mvc_project.domain.entities.PetEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends CrudRepository<PetEntity, Long>,
        PagingAndSortingRepository<PetEntity, Long> {
}
