package com.example.mvc_project.repositories;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.Pet;
import com.example.mvc_project.domain.PetCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PetRepositoryIntegrationTests {

    private PetRepository underTest;
    private PetCategoryRepository petCategoryDao;

    @Autowired
    public PetRepositoryIntegrationTests(PetRepository underTest, PetCategoryRepository petCategoryDao) {
        this.underTest = underTest;
        this.petCategoryDao = petCategoryDao;
    }

    @Test
    public void TestThatPetCanBeCreatedAndRecalled() {
        PetCategory petCategory = TestDataUtil.createTestPetCategoryA();
        PetCategory savedPetCategory = petCategoryDao.save(petCategory);

        Pet pet = TestDataUtil.createTestPetA(savedPetCategory);
        Pet savedPet = underTest.save(pet);
        Optional<Pet> result = underTest.findById(savedPet.getPetId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(pet);
    }

    @Test
    public void TestThatMultiplePetsCanBeCreatedAndRecalled() {
        PetCategory petCategoryA = TestDataUtil.createTestPetCategoryA();
        PetCategory savedPetCategoryA = petCategoryDao.save(petCategoryA);

        PetCategory petCategoryB = TestDataUtil.createTestPetCategoryB();
        PetCategory savedPetCategoryB =petCategoryDao.save(petCategoryB);

        PetCategory petCategoryC = TestDataUtil.createTestPetCategoryC();
        PetCategory savedPetCategoryC =petCategoryDao.save(petCategoryC);

        Pet petA = TestDataUtil.createTestPetA(savedPetCategoryA);
        underTest.save(petA);

        Pet petB = TestDataUtil.createTestPetB(savedPetCategoryB);
        underTest.save(petB);

        Pet petC = TestDataUtil.createTestPetC(savedPetCategoryC);
        underTest.save(petC);

        Iterable<Pet> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(petA, petB, petC);
    }

    @Test
    public void testThatPetCanBeUpdated() {
        PetCategory petCategoryA = TestDataUtil.createTestPetCategoryA();
        PetCategory savedPetCategory = petCategoryDao.save(petCategoryA);

        Pet petA = TestDataUtil.createTestPetA(savedPetCategory);
        underTest.save(petA);
        petA.setPetName("Biggg Gatto");
        Pet savedPet = underTest.save(petA);
        Optional<Pet> result = underTest.findById(savedPet.getPetId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(petA);
    }

    @Test
    public void testThatPetCanBeDeleted() {
        PetCategory petCategoryA = TestDataUtil.createTestPetCategoryA();
        PetCategory savedPetCategory = petCategoryDao.save(petCategoryA);

        Pet petA = TestDataUtil.createTestPetA(savedPetCategory);
        Pet savedPet = underTest.save(petA);
        underTest.deleteById(savedPet.getPetId());
        Optional<Pet> result = underTest.findById(savedPet.getPetId());
        assertThat(result).isEmpty();
    }

}
