package com.example.mvc_project.dao.impl;

import com.example.mvc_project.TestDataUtil;
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
public class PetCategoryDaoImplIntegrationTests {

    private PetCategoryDaoImpl underTest;

    @Autowired
    public PetCategoryDaoImplIntegrationTests(PetCategoryDaoImpl underTest){
        this.underTest = underTest;
    }

    @Test
    public void testThatPetCategoryCanBeCreatedAndRecalled(){
        PetCategory petCategory = TestDataUtil.createTestPetCategoryA();

        underTest.create(petCategory);
        Optional<PetCategory> result = underTest.findOne(petCategory.getPetCategoryId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(petCategory);
    }

    @Test
    public void testThatMultiplePetCategoryCanBeCreatedAndRecalled() {
        PetCategory petCategoryA = TestDataUtil.createTestPetCategoryA();
        underTest.create(petCategoryA);
        PetCategory petCategoryB = TestDataUtil.createTestPetCategoryB();
        underTest.create(petCategoryB);
        PetCategory petCategoryC = TestDataUtil.createTestPetCategoryC();
        underTest.create(petCategoryC);

        List<PetCategory> result = underTest.find();
        assertThat(result)
                .hasSize(3)
                .containsExactly(petCategoryA, petCategoryB, petCategoryC);
    }

    @Test
    public void testThatPetCategoryCanBeUpdated() {
        PetCategory petCategoryA = TestDataUtil.createTestPetCategoryA();
        underTest.create(petCategoryA);
        petCategoryA.setPetType("fish");
        underTest.update(petCategoryA.getPetCategoryId(), petCategoryA);
        Optional<PetCategory> result = underTest.findOne(petCategoryA.getPetCategoryId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(petCategoryA);
    }
}
