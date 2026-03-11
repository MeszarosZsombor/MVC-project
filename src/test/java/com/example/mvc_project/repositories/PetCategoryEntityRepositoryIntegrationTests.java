package com.example.mvc_project.repositories;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.entities.PetCategoryEntity;
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
public class PetCategoryEntityRepositoryIntegrationTests {

    private PetCategoryRepository underTest;

    @Autowired
    public PetCategoryEntityRepositoryIntegrationTests(PetCategoryRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void testThatPetCategoryCanBeCreatedAndRecalled(){
        PetCategoryEntity petCategoryEntity = TestDataUtil.createTestPetCategoryA();

        PetCategoryEntity savedPetCategoryEntity = underTest.save(petCategoryEntity);
        Optional<PetCategoryEntity> result = underTest.findById(savedPetCategoryEntity.getPetCategoryId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(petCategoryEntity);
    }

    @Test
    public void testThatMultiplePetCategoryCanBeCreatedAndRecalled() {
        PetCategoryEntity petCategoryEntityA = TestDataUtil.createTestPetCategoryA();
        underTest.save(petCategoryEntityA);
        PetCategoryEntity petCategoryEntityB = TestDataUtil.createTestPetCategoryB();
        underTest.save(petCategoryEntityB);
        PetCategoryEntity petCategoryEntityC = TestDataUtil.createTestPetCategoryC();
        underTest.save(petCategoryEntityC);

        Iterable<PetCategoryEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(petCategoryEntityA, petCategoryEntityB, petCategoryEntityC);
    }

    @Test
    public void testThatPetCategoryCanBeUpdated() {
        PetCategoryEntity petCategoryEntityA = TestDataUtil.createTestPetCategoryA();
        underTest.save(petCategoryEntityA);
        petCategoryEntityA.setPetType("fish");
        PetCategoryEntity savedPetCategoryEntity = underTest.save(petCategoryEntityA);
        Optional<PetCategoryEntity> result = underTest.findById(savedPetCategoryEntity.getPetCategoryId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(petCategoryEntityA);
    }

    @Test
    public void testThatPetCategoryCanBeDeleted() {
        PetCategoryEntity petCategoryEntityA = TestDataUtil.createTestPetCategoryA();
        PetCategoryEntity savedPetCategoryEntity = underTest.save(petCategoryEntityA);
        underTest.deleteById(savedPetCategoryEntity.getPetCategoryId());
        Optional<PetCategoryEntity> result = underTest.findById(savedPetCategoryEntity.getPetCategoryId());
        assertThat(result).isEmpty();
    }
}
