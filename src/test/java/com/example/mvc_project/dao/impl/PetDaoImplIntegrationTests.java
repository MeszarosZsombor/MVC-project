package com.example.mvc_project.dao.impl;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.Pet;
import com.example.mvc_project.domain.PetCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
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
        PetCategory petCategory = TestDataUtil.createTestPetCategory();
        petCategoryDao.create(petCategory);

        Pet pet = TestDataUtil.createTestPet();

        underTest.create(pet);
        Optional<Pet> result = underTest.findOne(pet.getPetId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(pet);
    }
}
