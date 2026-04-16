package com.example.mvc_project;


import com.example.mvc_project.domain.dto.AdoptionDto;
import com.example.mvc_project.domain.dto.OwnerDto;
import com.example.mvc_project.domain.dto.PetCategoryDto;
import com.example.mvc_project.domain.dto.PetDto;
import com.example.mvc_project.domain.entities.AdoptionEntity;
import com.example.mvc_project.domain.entities.OwnerEntity;
import com.example.mvc_project.domain.entities.PetEntity;
import com.example.mvc_project.domain.entities.PetCategoryEntity;

public final class TestDataUtil {
    private TestDataUtil(){}

    public static OwnerEntity createTestOwnerA() {
        return OwnerEntity.builder()
                .role("user")
                .email("test@email.com")
                .password("password")
                .name("John Doe")
                .build();
    }

    public static OwnerDto createTestOwnerDtoA() {
        return OwnerDto.builder()
                .role("user")
                .email("test@email.com")
                .password("password")
                .name("John Doe")
                .build();
    }

    public static OwnerEntity createTestOwnerB() {
        return OwnerEntity.builder()
                .role("admin")
                .email("test22@email.com")
                .password("password123")
                .name("Thomas Rose")
                .build();
    }

    public static OwnerDto createTestOwnerDtoB() {
        return OwnerDto.builder()
                .role("admin")
                .email("test22@email.com")
                .password("password123")
                .name("Thomas Rose")
                .build();
    }

    public static OwnerEntity createTestOwnerC() {
        return OwnerEntity.builder()
                .role("user")
                .email("test989@email.com")
                .password("pas")
                .name("Jerry Crown")
                .build();
    }

    public static OwnerEntity createTestOwnerD() {
        return OwnerEntity.builder()
                .role("user")
                .email("test897997@email.com")
                .password("pwd")
                .name("Abigail Cronin")
                .build();
    }

    public static PetEntity createTestPetA(final PetCategoryEntity petCategoryEntity) {
        return PetEntity.builder()
                .petName("Gato")
                .weight(10)
                .age(3)
                .gender("M")
                .petCategory(petCategoryEntity)
                .adopted(true)
                .build();
    }


    public static PetDto createTestPetDtoA() {
        return PetDto.builder()
                .petName("Gato")
                .weight(10)
                .age(3)
                .gender("M")
                .adopted(true)
                .build();
    }

    public static PetEntity createTestPetB(final PetCategoryEntity petCategoryEntity) {
        return PetEntity.builder()
                .petName("Doggo")
                .weight(15)
                .age(3)
                .gender("F")
                .petCategory(petCategoryEntity)
                .adopted(true)
                .build();
    }

    public static PetDto createTestPetDtoB() {
        return PetDto.builder()
                .petName("Doggo")
                .weight(15)
                .age(3)
                .gender("F")
                .adopted(true)
                .build();
    }

    public static PetEntity createTestPetC(final PetCategoryEntity petCategoryEntity) {
        return PetEntity.builder()
                .petName("Hamsto")
                .weight(1)
                .age(1)
                .gender("M")
                .petCategory(petCategoryEntity)
                .adopted(false)
                .build();
    }

    public static PetCategoryEntity createTestPetCategoryA() {
        return PetCategoryEntity.builder()
                .petType("Cat")
                .build();
    }

    public static PetCategoryDto createTestPetCategoryDtoA() {
        return PetCategoryDto.builder()
                .petType("Cat")
                .build();
    }

    public static PetCategoryEntity createTestPetCategoryB() {
        return PetCategoryEntity.builder()
                .petType("Dog")
                .build();
    }

    public static PetCategoryDto createTestPetCategoryDtoB() {
        return PetCategoryDto.builder()
                .petType("Dog")
                .build();
    }

    public static PetCategoryEntity createTestPetCategoryC() {
        return PetCategoryEntity.builder()
                .petType("Hamster")
                .build();
    }

    public static AdoptionEntity createTestAdoptionA(final OwnerEntity ownerEntity, final PetEntity petEntity) {
        return  AdoptionEntity.builder()
                .owner(ownerEntity)
                .pet(petEntity)
                .build();
    }

    public static AdoptionDto createTestAdoptionDtoA() {
        return  AdoptionDto.builder()
                .build();
    }


    public static AdoptionEntity createTestAdoptionB(final OwnerEntity ownerEntity, final PetEntity petEntity) {
        return  AdoptionEntity.builder()
                .owner(ownerEntity)
                .pet(petEntity)
                .build();
    }

    public static AdoptionDto createTestAdoptionDtoB() {
        return  AdoptionDto.builder()
                .build();
    }

    public static AdoptionEntity createTestAdoptionC(final OwnerEntity ownerEntity, final PetEntity petEntity) {
        return  AdoptionEntity.builder()
                .owner(ownerEntity)
                .pet(petEntity)
                .build();
    }
}
