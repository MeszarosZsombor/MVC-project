package com.example.mvc_project.repositories;

import com.example.mvc_project.domain.Owner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Long> {
    Iterable<Owner> roleIs(String role);
}
