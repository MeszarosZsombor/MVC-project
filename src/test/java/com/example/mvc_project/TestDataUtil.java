package com.example.mvc_project;


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

    public static OwnerEntity createTestOwnerB() {
        return OwnerEntity.builder()
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
                .petCategoryEntity(petCategoryEntity)
                .adopted(true)
                .build();
    }

    public static PetEntity createTestPetB(final PetCategoryEntity petCategoryEntity) {
        return PetEntity.builder()
                .petName("Doggo")
                .weight(15)
                .age(3)
                .gender("F")
                .petCategoryEntity(petCategoryEntity)
                .adopted(true)
                .build();
    }

    public static PetEntity createTestPetC(final PetCategoryEntity petCategoryEntity) {
        return PetEntity.builder()
                .petName("Hamsto")
                .weight(1)
                .age(1)
                .gender("M")
                .petCategoryEntity(petCategoryEntity)
                .adopted(false)
                .build();
    }

    public static PetCategoryEntity createTestPetCategoryA() {
        return PetCategoryEntity.builder()
                .petType("cat")
                .build();
    }

    public static PetCategoryEntity createTestPetCategoryB() {
        return PetCategoryEntity.builder()
                .petType("dog")
                .build();
    }

    public static PetCategoryEntity createTestPetCategoryC() {
        return PetCategoryEntity.builder()
                .petType("hamster")
                .build();
    }

    public static AdoptionEntity createTestAdoptionA(final OwnerEntity ownerEntity, final PetEntity petEntity) {
        return  AdoptionEntity.builder()
                .ownerEntity(ownerEntity)
                .petEntity(petEntity)
                .build();
    }

    public static AdoptionEntity createTestAdoptionB(final OwnerEntity ownerEntity, final PetEntity petEntity) {
        return  AdoptionEntity.builder()
                .ownerEntity(ownerEntity)
                .petEntity(petEntity)
                .build();
    }

    public static AdoptionEntity createTestAdoptionC(final OwnerEntity ownerEntity, final PetEntity petEntity) {
        return  AdoptionEntity.builder()
                .ownerEntity(ownerEntity)
                .petEntity(petEntity)
                .build();
    }
}
