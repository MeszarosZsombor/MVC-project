package com.example.mvc_project.repositories;

import com.example.mvc_project.TestDataUtil;
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
public class PetCategoryRepositoryIntegrationTests {

    private PetCategoryRepository underTest;

    @Autowired
    public PetCategoryRepositoryIntegrationTests(PetCategoryRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void testThatPetCategoryCanBeCreatedAndRecalled(){
        PetCategory petCategory = TestDataUtil.createTestPetCategoryA();

        PetCategory savedPetCategory = underTest.save(petCategory);
        Optional<PetCategory> result = underTest.findById(savedPetCategory.getPetCategoryId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(petCategory);
    }

    @Test
    public void testThatMultiplePetCategoryCanBeCreatedAndRecalled() {
        PetCategory petCategoryA = TestDataUtil.createTestPetCategoryA();
        underTest.save(petCategoryA);
        PetCategory petCategoryB = TestDataUtil.createTestPetCategoryB();
        underTest.save(petCategoryB);
        PetCategory petCategoryC = TestDataUtil.createTestPetCategoryC();
        underTest.save(petCategoryC);

        Iterable<PetCategory> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(petCategoryA, petCategoryB, petCategoryC);
    }

    @Test
    public void testThatPetCategoryCanBeUpdated() {
        PetCategory petCategoryA = TestDataUtil.createTestPetCategoryA();
        underTest.save(petCategoryA);
        petCategoryA.setPetType("fish");
        PetCategory savedPetCategory = underTest.save(petCategoryA);
        Optional<PetCategory> result = underTest.findById(savedPetCategory.getPetCategoryId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(petCategoryA);
    }

    @Test
    public void testThatPetCategoryCanBeDeleted() {
        PetCategory petCategoryA = TestDataUtil.createTestPetCategoryA();
        PetCategory savedPetCategory = underTest.save(petCategoryA);
        underTest.deleteById(savedPetCategory.getPetCategoryId());
        Optional<PetCategory> result = underTest.findById(savedPetCategory.getPetCategoryId());
        assertThat(result).isEmpty();
    }
}
