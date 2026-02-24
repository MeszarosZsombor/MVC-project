package com.example.mvc_project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Owner {

    private Long ownerId;
    private String role;
    private String email;
    private String password;
    private String name;
    private OffsetDateTime createdAt;

}
