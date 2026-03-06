package com.example.mvc_project.repositories;

import com.example.mvc_project.domain.PetCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetCategoryRepository extends CrudRepository<PetCategory, Long> {
}
