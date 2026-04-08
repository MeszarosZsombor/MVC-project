package com.example.mvc_project.repositories;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.entities.AdoptionEntity;
import com.example.mvc_project.domain.entities.OwnerEntity;
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
public class AdoptionEntityRepositoryIntegrationTests {

    private AdoptionRepository underTest;
    private PetCategoryRepository petCategoryDao;
    private PetRepository petDao;
    private OwnerRepository ownerDao;

    @Autowired
    public AdoptionEntityRepositoryIntegrationTests(AdoptionRepository underTest, PetCategoryRepository petCategoryDao,
                                                    PetRepository petDao, OwnerRepository ownerDao){
        this.underTest = underTest;
        this.petCategoryDao = petCategoryDao;
        this.petDao = petDao;
        this.ownerDao = ownerDao;
    }

    @Test
    public void testThatAdoptionCanBeCreatedAndRecalled() {
        OwnerEntity ownerEntity = TestDataUtil.createTestOwnerA();
        OwnerEntity savedOwnerEntity = ownerDao.save(ownerEntity);

        PetCategoryEntity petCategoryEntity = TestDataUtil.createTestPetCategoryA();
        PetCategoryEntity savedPetCategoryEntity = petCategoryDao.save(petCategoryEntity);

        PetEntity petEntity = TestDataUtil.createTestPetA(savedPetCategoryEntity);
        PetEntity savedPetEntity = petDao.save(petEntity);

        AdoptionEntity adoptionEntity = TestDataUtil.createTestAdoptionA(savedOwnerEntity, savedPetEntity);
        AdoptionEntity savedAdoptionEntity = underTest.save(adoptionEntity);
        Optional<AdoptionEntity> result = underTest.findById(savedAdoptionEntity.getAdoptionId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(adoptionEntity);
    }

    @Test
    public void testThatMultipleAdoptionsCanBeCreatedAndRecalled() {
        OwnerEntity ownerEntityA = TestDataUtil.createTestOwnerA();
        OwnerEntity savedOwnerEntityA = ownerDao.save(ownerEntityA);
        PetCategoryEntity petCategoryEntityA = TestDataUtil.createTestPetCategoryA();
        PetCategoryEntity savedPetCategoryEntityA = petCategoryDao.save(petCategoryEntityA);
        PetEntity petEntityA = TestDataUtil.createTestPetA(savedPetCategoryEntityA);
        PetEntity savedPetEntityA = petDao.save(petEntityA);
        AdoptionEntity adoptionEntityA = TestDataUtil.createTestAdoptionA(savedOwnerEntityA, savedPetEntityA);
        underTest.save(adoptionEntityA);

        OwnerEntity ownerEntityB = TestDataUtil.createTestOwnerB();
        OwnerEntity savedOwnerEntityB = ownerDao.save(ownerEntityB);
        PetCategoryEntity petCategoryEntityB = TestDataUtil.createTestPetCategoryB();
        PetCategoryEntity savedPetCategoryEntityB = petCategoryDao.save(petCategoryEntityB);
        PetEntity petEntityB = TestDataUtil.createTestPetB(savedPetCategoryEntityB);
        PetEntity savedPetEntityB = petDao.save(petEntityB);
        AdoptionEntity adoptionEntityB = TestDataUtil.createTestAdoptionB(savedOwnerEntityB, savedPetEntityB);
        underTest.save(adoptionEntityB);

        OwnerEntity ownerEntityC = TestDataUtil.createTestOwnerC();
        OwnerEntity savedOwnerEntityC = ownerDao.save(ownerEntityC);
        PetCategoryEntity petCategoryEntityC = TestDataUtil.createTestPetCategoryC();
        PetCategoryEntity savedPetCategoryEntityC = petCategoryDao.save(petCategoryEntityC);
        PetEntity petEntityC = TestDataUtil.createTestPetC(savedPetCategoryEntityC);
        PetEntity savedPetEntityC = petDao.save(petEntityC);
        AdoptionEntity adoptionEntityC = TestDataUtil.createTestAdoptionC(savedOwnerEntityC, savedPetEntityC);
        underTest.save(adoptionEntityC);

        Iterable<AdoptionEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(adoptionEntityA, adoptionEntityB, adoptionEntityC);
    }

    @Test
    public void testThatAdoptionCanBeUpdated() {
        OwnerEntity ownerEntityA = TestDataUtil.createTestOwnerA();
        OwnerEntity savedOwnerEntity = ownerDao.save(ownerEntityA);

        PetCategoryEntity petCategoryEntityA = TestDataUtil.createTestPetCategoryA();
        PetCategoryEntity savedPetCategoryEntityA = petCategoryDao.save(petCategoryEntityA);
        PetCategoryEntity petCategoryEntityB = TestDataUtil.createTestPetCategoryB();
        PetCategoryEntity savedPetCategoryEntityB = petCategoryDao.save(petCategoryEntityB);

        PetEntity petEntityA = TestDataUtil.createTestPetA(savedPetCategoryEntityA);
        PetEntity savedPetEntityA = petDao.save(petEntityA);
        PetEntity petEntityB = TestDataUtil.createTestPetB(savedPetCategoryEntityB);
        PetEntity savedPetEntityB = petDao.save(petEntityB);

        AdoptionEntity adoptionEntityA = TestDataUtil.createTestAdoptionA(savedOwnerEntity, savedPetEntityA);
        underTest.save(adoptionEntityA);
        adoptionEntityA.setPet(savedPetEntityB);
        AdoptionEntity savedAdoptionEntity = underTest.save(adoptionEntityA);

        Optional<AdoptionEntity> result = underTest.findById(savedAdoptionEntity.getAdoptionId());
        assertThat(result).isPresent();
        assertThat(result.get())
                .usingRecursiveComparison()
                .ignoringFields("updatedAt")
                .isEqualTo(adoptionEntityA);
    }

    @Test
    public void testThatAdoptionCanBeDeleted() {
        OwnerEntity ownerEntityA = TestDataUtil.createTestOwnerA();
        OwnerEntity savedOwnerEntity = ownerDao.save(ownerEntityA);

        PetCategoryEntity petCategoryEntityA = TestDataUtil.createTestPetCategoryA();
        PetCategoryEntity savedPetCategoryEntity = petCategoryDao.save(petCategoryEntityA);

        PetEntity petEntityA = TestDataUtil.createTestPetA(savedPetCategoryEntity);
        PetEntity savedPetEntity = petDao.save(petEntityA);

        AdoptionEntity adoptionEntityA = TestDataUtil.createTestAdoptionA(savedOwnerEntity, savedPetEntity);
        AdoptionEntity savedAdoptionEntity = underTest.save(adoptionEntityA);

        underTest.deleteById(savedAdoptionEntity.getAdoptionId());
        Optional<AdoptionEntity> result = underTest.findById(savedAdoptionEntity.getAdoptionId());
        assertThat(result).isEmpty();
    }
}
