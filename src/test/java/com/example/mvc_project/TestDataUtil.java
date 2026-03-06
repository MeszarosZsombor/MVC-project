package com.example.mvc_project;


import com.example.mvc_project.domain.Adoption;
import com.example.mvc_project.domain.Owner;
import com.example.mvc_project.domain.Pet;
import com.example.mvc_project.domain.PetCategory;

public final class TestDataUtil {
    private TestDataUtil(){}

    public static Owner createTestOwnerA() {
        return Owner.builder()
                .role("user")
                .email("test@email.com")
                .password("password")
                .name("John Doe")
                .build();
    }

    public static Owner createTestOwnerB() {
        return Owner.builder()
                .role("admin")
                .email("test22@email.com")
                .password("password123")
                .name("Thomas Rose")
                .build();
    }

    public static Owner createTestOwnerC() {
        return Owner.builder()
                .role("user")
                .email("test989@email.com")
                .password("pas")
                .name("Jerry Crown")
                .build();
    }

    public static Owner createTestOwnerD() {
        return Owner.builder()
                .role("user")
                .email("test897997@email.com")
                .password("pwd")
                .name("Abigail Cronin")
                .build();
    }

    public static Pet createTestPetA(final PetCategory petCategory) {
        return Pet.builder()
                .petName("Gato")
                .weight(10)
                .age(3)
                .gender("M")
                .petCategory(petCategory)
                .adopted(true)
                .build();
    }

    public static Pet createTestPetB(final PetCategory petCategory) {
        return Pet.builder()
                .petName("Doggo")
                .weight(15)
                .age(3)
                .gender("F")
                .petCategory(petCategory)
                .adopted(true)
                .build();
    }

    public static Pet createTestPetC(final PetCategory petCategory) {
        return Pet.builder()
                .petName("Hamsto")
                .weight(1)
                .age(1)
                .gender("M")
                .petCategory(petCategory)
                .adopted(false)
                .build();
    }

    public static PetCategory createTestPetCategoryA() {
        return PetCategory.builder()
                .petType("cat")
                .build();
    }

    public static PetCategory createTestPetCategoryB() {
        return PetCategory.builder()
                .petType("dog")
                .build();
    }

    public static PetCategory createTestPetCategoryC() {
        return PetCategory.builder()
                .petType("hamster")
                .build();
    }

    public static Adoption createTestAdoptionA(final Owner owner, final Pet pet) {
        return  Adoption.builder()
                .owner(owner)
                .pet(pet)
                .build();
    }

    public static Adoption createTestAdoptionB(final Owner owner, final Pet pet) {
        return  Adoption.builder()
                .owner(owner)
                .pet(pet)
                .build();
    }

    public static Adoption createTestAdoptionC(final Owner owner, final Pet pet) {
        return  Adoption.builder()
                .owner(owner)
                .pet(pet)
                .build();
    }
}
