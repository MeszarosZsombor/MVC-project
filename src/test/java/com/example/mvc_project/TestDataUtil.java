package com.example.mvc_project;


import com.example.mvc_project.domain.Owner;
import com.example.mvc_project.domain.Pet;
import com.example.mvc_project.domain.PetCategory;

import java.sql.Time;
import java.time.LocalTime;

public final class TestDataUtil {
    private TestDataUtil(){}

    public static Owner createTestOwner() {
        return Owner.builder()
                .ownerId(1L)
                .role("user")
                .email("test@email.com")
                .password("password")
                .name("John Doe")
                .build();
    }

    public static Pet createTestPet() {
        return Pet.builder()
                .petId(2L)
                .petName("Gato")
                .weight(10)
                .age(3)
                .gender("M")
                .petCategoryId(1L)
                .adopted(true)
                .build();
    }

    public static PetCategory createTestPetCategory() {
        return PetCategory.builder()
                .petCategoryId(1L)
                .petType("cat")
                .build();
    }
}
