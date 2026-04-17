package com.example.mvc_project.services.impl;

import com.example.mvc_project.domain.entities.OwnerEntity;
import com.example.mvc_project.repositories.OwnerRepository;
import com.example.mvc_project.services.OwnerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OwnerServiceImpl implements OwnerService {

    private OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    public OwnerServiceImpl(OwnerRepository ownerRepository, PasswordEncoder passwordEncoder, PasswordEncoder passwordEncoder1) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public OwnerEntity save(OwnerEntity ownerEntity) {

        boolean conflict = (ownerEntity.getOwnerId() == null)
                ? ownerRepository.existsByEmail(ownerEntity.getEmail())
                : ownerRepository.existsByEmailAndOwnerIdNot(ownerEntity.getEmail(), ownerEntity.getOwnerId());

        if (conflict) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        ownerEntity.setRole("user");
        ownerEntity.setPassword(passwordEncoder.encode(ownerEntity.getPassword()));

        return ownerRepository.save(ownerEntity);
    }

    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

    @Override
    public List<OwnerEntity> findAll() {
        return StreamSupport.stream(ownerRepository
                .findAll()
                .spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<OwnerEntity> findAll(Pageable pageable) {
        return ownerRepository.findAll(pageable);
    }

    @Override
    public Optional<OwnerEntity> findOne(Long id) {
        return ownerRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return ownerRepository.existsById(id);
    }

    @Override
    public OwnerEntity partialUpdate(Long id, OwnerEntity ownerEntity) {
        ownerEntity.setOwnerId(id);

        return ownerRepository.findById(id).map(existingOwner -> {
            Optional.ofNullable(ownerEntity.getRole()).ifPresent(existingOwner::setRole);
            Optional.ofNullable(ownerEntity.getName()).ifPresent(existingOwner::setName);
            Optional.ofNullable(ownerEntity.getEmail()).ifPresent(existingOwner::setEmail);
            Optional.ofNullable(ownerEntity.getPassword()).ifPresent(existingOwner::setPassword);
            return ownerRepository.save(existingOwner);
        }).orElseThrow(() -> new RuntimeException("Owner does not exists"));
    }

    @Override
    public void delete(Long id) {
        ownerRepository.deleteById(id);
    }
}
