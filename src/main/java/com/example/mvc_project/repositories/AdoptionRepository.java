package com.example.mvc_project.repositories;

import com.example.mvc_project.domain.Adoption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptionRepository extends CrudRepository<Adoption, Long> {
}
