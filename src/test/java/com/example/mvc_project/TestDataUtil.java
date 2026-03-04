package com.example.mvc_project;


import com.example.mvc_project.domain.Adoption;
import com.example.mvc_project.domain.Owner;
import com.example.mvc_project.domain.Pet;
import com.example.mvc_project.domain.PetCategory;

public final class TestDataUtil {
    private TestDataUtil(){}

    public static Owner createTestOwnerA() {
        return Owner.builder()
                .ownerId(1L)
                .role("user")
                .email("test@email.com")
                .password("password")
                .name("John Doe")
                .build();
    }

    public static Owner createTestOwnerB() {
        return Owner.builder()
                .ownerId(2L)
                .role("admin")
                .email("test22@email.com")
                .password("password123")
                .name("Thomas Rose")
                .build();
    }

    public static Owner createTestOwnerC() {
        return Owner.builder()
                .ownerId(3L)
                .role("user")
                .email("test989@email.com")
                .password("pas")
                .name("Jerry Crown")
                .build();
    }

    public static Owner createTestOwnerD() {
        return Owner.builder()
                .ownerId(4L)
                .role("user")
                .email("test897997@email.com")
                .password("pwd")
                .name("Abigail Cronin")
                .build();
    }

    public static Pet createTestPetA() {
        return Pet.builder()
                .petId(1L)
                .petName("Gato")
                .weight(10)
                .age(3)
                .gender("M")
                .petCategoryId(1L)
                .adopted(true)
                .build();
    }

    public static Pet createTestPetB() {
        return Pet.builder()
                .petId(2L)
                .petName("Doggo")
                .weight(15)
                .age(3)
                .gender("F")
                .petCategoryId(2L)
                .adopted(true)
                .build();
    }

    public static Pet createTestPetC() {
        return Pet.builder()
                .petId(3L)
                .petName("Hamsto")
                .weight(1)
                .age(1)
                .gender("M")
                .petCategoryId(3L)
                .adopted(false)
                .build();
    }

    public static PetCategory createTestPetCategoryA() {
        return PetCategory.builder()
                .petCategoryId(1L)
                .petType("cat")
                .build();
    }

    public static PetCategory createTestPetCategoryB() {
        return PetCategory.builder()
                .petCategoryId(2L)
                .petType("dog")
                .build();
    }

    public static PetCategory createTestPetCategoryC() {
        return PetCategory.builder()
                .petCategoryId(3L)
                .petType("hamster")
                .build();
    }

    public static Adoption createTestAdoptionA() {
        return  Adoption.builder()
                .adoptionId(1L)
                .ownerId(1L)
                .petId(2L)
                .build();
    }

    public static Adoption createTestAdoptionB() {
        return  Adoption.builder()
                .adoptionId(2L)
                .ownerId(1L)
                .petId(1L)
                .build();
    }

    public static Adoption createTestAdoptionC() {
        return  Adoption.builder()
                .adoptionId(3L)
                .ownerId(3L)
                .petId(1L)
                .build();
    }
}
