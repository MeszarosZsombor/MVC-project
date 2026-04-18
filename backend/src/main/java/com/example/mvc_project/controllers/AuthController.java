package com.example.mvc_project.controllers;

import com.example.mvc_project.domain.dto.LoginRequestDto;
import com.example.mvc_project.domain.dto.LoginResponseDto;
import com.example.mvc_project.domain.dto.OwnerDto;
import com.example.mvc_project.domain.entities.OwnerEntity;
import com.example.mvc_project.mappers.Mapper;
import com.example.mvc_project.repositories.OwnerRepository;
import com.example.mvc_project.security.JwtService;
import com.example.mvc_project.services.OwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class AuthController {

    private OwnerService ownerService;
    private Mapper<OwnerEntity, OwnerDto> ownerMapper;
    private OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    public AuthController(OwnerService ownerService, Mapper<OwnerEntity, OwnerDto> ownerMapper,
                           OwnerRepository ownerRepository, PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.ownerService = ownerService;
        this.ownerMapper = ownerMapper;
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        OwnerEntity owner = ownerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password is incorrect"));

        if (!passwordEncoder.matches(request.getPassword(), owner.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password is incorrect");
        }

        String token = jwtService.generateToken(owner.getEmail());

        return ResponseEntity.ok(new LoginResponseDto(token, owner.getEmail(), owner.getName()));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<LoginResponseDto> register(@RequestBody OwnerDto owner) {
        OwnerEntity ownerEntity = ownerMapper.mapFrom(owner);
        OwnerEntity savedOwnerEntity = ownerService.save(ownerEntity);
        String token = jwtService.generateToken(savedOwnerEntity.getEmail());

        return new ResponseEntity<>(
                new LoginResponseDto(token,
                        savedOwnerEntity.getEmail(),
                        savedOwnerEntity.getName()
                ), HttpStatus.CREATED
        );
    }
}
