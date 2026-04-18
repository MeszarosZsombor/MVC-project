package com.example.mvc_project.repositories;

import com.example.mvc_project.domain.entities.OwnerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends CrudRepository<OwnerEntity, Long>,
        PagingAndSortingRepository<OwnerEntity, Long> {
    Iterable<OwnerEntity> roleIs(String role);
    boolean existsByEmail(String email);
    boolean existsByEmailAndOwnerIdNot(String email, Long id);

    Optional<OwnerEntity> findByEmail(String email);
}
