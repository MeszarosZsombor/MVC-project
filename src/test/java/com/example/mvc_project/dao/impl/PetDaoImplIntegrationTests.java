package com.example.mvc_project.dao.impl;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.Pet;
import com.example.mvc_project.domain.PetCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PetDaoImplIntegrationTests {

    private PetDaoImpl underTest;
    private PetCategoryDaoImpl petCategoryDao;

    @Autowired
    public PetDaoImplIntegrationTests(PetDaoImpl underTest, PetCategoryDaoImpl petCategoryDao) {
        this.underTest = underTest;
        this.petCategoryDao = petCategoryDao;
    }

    @Test
    public void TestThatPetCanBeCreatedAndRecalled() {
        PetCategory petCategory = TestDataUtil.createTestPetCategoryA();
        petCategoryDao.create(petCategory);

        Pet pet = TestDataUtil.createTestPetA();
        pet.setPetCategoryId(petCategory.getPetCategoryId());
        underTest.create(pet);
        Optional<Pet> result = underTest.findOne(pet.getPetId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(pet);
    }

    @Test
    public void TestThatMultiplePetsCanBeCreatedAndRecalled() {
        PetCategory petCategoryA = TestDataUtil.createTestPetCategoryA();
        petCategoryDao.create(petCategoryA);

        PetCategory petCategoryB = TestDataUtil.createTestPetCategoryB();
        petCategoryDao.create(petCategoryB);

        PetCategory petCategoryC = TestDataUtil.createTestPetCategoryC();
        petCategoryDao.create(petCategoryC);

        Pet petA = TestDataUtil.createTestPetA();
        petA.setPetCategoryId(petA.getPetCategoryId());
        underTest.create(petA);

        Pet petB = TestDataUtil.createTestPetB();
        petB.setPetCategoryId(petB.getPetCategoryId());
        underTest.create(petB);

        Pet petC = TestDataUtil.createTestPetC();
        petC.setPetCategoryId(petC.getPetCategoryId());
        underTest.create(petC);

        List<Pet> result = underTest.find();
        assertThat(result)
                .hasSize(3)
                .containsExactly(petA, petB, petC);
    }

    @Test
    public void testThatPetCanBeUpdated() {
        PetCategory petCategoryA = TestDataUtil.createTestPetCategoryA();
        petCategoryDao.create(petCategoryA);

        Pet petA = TestDataUtil.createTestPetA();
        petA.setPetCategoryId(petA.getPetCategoryId());
        underTest.create(petA);
        petA.setPetName("Biggg Gatto");
        underTest.update(petA.getPetId(), petA);
        Optional<Pet> result = underTest.findOne(petA.getPetId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(petA);
    }

    @Test
    public void testThatPetCanBeDeleted() {
        PetCategory petCategoryA = TestDataUtil.createTestPetCategoryA();
        petCategoryDao.create(petCategoryA);

        Pet petA = TestDataUtil.createTestPetA();
        petA.setPetCategoryId(petA.getPetCategoryId());
        underTest.create(petA);
        underTest.delete(petA.getPetId());
        Optional<Pet> result = underTest.findOne(petA.getPetId());
        assertThat(result).isEmpty();
    }

}
