package com.example.mvc_project.repositories;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.entities.PetEntity;
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
public class PetEntityRepositoryIntegrationTests {

    private PetRepository underTest;
    private PetCategoryRepository petCategoryDao;

    @Autowired
    public PetEntityRepositoryIntegrationTests(PetRepository underTest, PetCategoryRepository petCategoryDao) {
        this.underTest = underTest;
        this.petCategoryDao = petCategoryDao;
    }

    @Test
    public void TestThatPetCanBeCreatedAndRecalled() {
        PetCategoryEntity petCategoryEntity = TestDataUtil.createTestPetCategoryA();
        PetCategoryEntity savedPetCategoryEntity = petCategoryDao.save(petCategoryEntity);

        PetEntity petEntity = TestDataUtil.createTestPetA(savedPetCategoryEntity);
        PetEntity savedPetEntity = underTest.save(petEntity);
        Optional<PetEntity> result = underTest.findById(savedPetEntity.getPetId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(petEntity);
    }

    @Test
    public void TestThatMultiplePetsCanBeCreatedAndRecalled() {
        PetCategoryEntity petCategoryEntityA = TestDataUtil.createTestPetCategoryA();
        PetCategoryEntity savedPetCategoryEntityA = petCategoryDao.save(petCategoryEntityA);

        PetCategoryEntity petCategoryEntityB = TestDataUtil.createTestPetCategoryB();
        PetCategoryEntity savedPetCategoryEntityB =petCategoryDao.save(petCategoryEntityB);

        PetCategoryEntity petCategoryEntityC = TestDataUtil.createTestPetCategoryC();
        PetCategoryEntity savedPetCategoryEntityC =petCategoryDao.save(petCategoryEntityC);

        PetEntity petEntityA = TestDataUtil.createTestPetA(savedPetCategoryEntityA);
        underTest.save(petEntityA);

        PetEntity petEntityB = TestDataUtil.createTestPetB(savedPetCategoryEntityB);
        underTest.save(petEntityB);

        PetEntity petEntityC = TestDataUtil.createTestPetC(savedPetCategoryEntityC);
        underTest.save(petEntityC);

        Iterable<PetEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(petEntityA, petEntityB, petEntityC);
    }

    @Test
    public void testThatPetCanBeUpdated() {
        PetCategoryEntity petCategoryEntityA = TestDataUtil.createTestPetCategoryA();
        PetCategoryEntity savedPetCategoryEntity = petCategoryDao.save(petCategoryEntityA);

        PetEntity petEntityA = TestDataUtil.createTestPetA(savedPetCategoryEntity);
        underTest.save(petEntityA);
        petEntityA.setPetName("Biggg Gatto");
        PetEntity savedPetEntity = underTest.save(petEntityA);
        Optional<PetEntity> result = underTest.findById(savedPetEntity.getPetId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(petEntityA);
    }

    @Test
    public void testThatPetCanBeDeleted() {
        PetCategoryEntity petCategoryEntityA = TestDataUtil.createTestPetCategoryA();
        PetCategoryEntity savedPetCategoryEntity = petCategoryDao.save(petCategoryEntityA);

        PetEntity petEntityA = TestDataUtil.createTestPetA(savedPetCategoryEntity);
        PetEntity savedPetEntity = underTest.save(petEntityA);
        underTest.deleteById(savedPetEntity.getPetId());
        Optional<PetEntity> result = underTest.findById(savedPetEntity.getPetId());
        assertThat(result).isEmpty();
    }

}
