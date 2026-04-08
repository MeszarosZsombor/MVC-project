package com.example.mvc_project.repositories;

import com.example.mvc_project.domain.entities.AdoptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptionRepository extends CrudRepository<AdoptionEntity, Long>,
        PagingAndSortingRepository<AdoptionEntity, Long> {
}
