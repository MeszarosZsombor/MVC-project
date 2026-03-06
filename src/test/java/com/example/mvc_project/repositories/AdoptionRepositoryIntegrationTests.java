package com.example.mvc_project.repositories;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.Adoption;
import com.example.mvc_project.domain.Owner;
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
public class AdoptionRepositoryIntegrationTests {

    private AdoptionRepository underTest;
    private PetCategoryRepository petCategoryDao;
    private PetRepository petDao;
    private OwnerRepository ownerDao;

    @Autowired
    public AdoptionRepositoryIntegrationTests(AdoptionRepository underTest, PetCategoryRepository petCategoryDao,
                                              PetRepository petDao, OwnerRepository ownerDao){
        this.underTest = underTest;
        this.petCategoryDao = petCategoryDao;
        this.petDao = petDao;
        this.ownerDao = ownerDao;
    }

    @Test
    public void testThatAdoptionCanBeCreatedAndRecalled() {
        Owner owner = TestDataUtil.createTestOwnerA();
        Owner savedOwner = ownerDao.save(owner);

        PetCategory petCategory = TestDataUtil.createTestPetCategoryA();
        PetCategory savedPetCategory = petCategoryDao.save(petCategory);

        Pet pet = TestDataUtil.createTestPetA(savedPetCategory);
        Pet savedPet = petDao.save(pet);

        Adoption adoption = TestDataUtil.createTestAdoptionA(savedOwner, savedPet);
        Adoption savedAdoption = underTest.save(adoption);
        Optional<Adoption> result = underTest.findById(savedAdoption.getAdoptionId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(adoption);
    }

    @Test
    public void testThatMultipleAdoptionsCanBeCreatedAndRecalled() {
        Owner ownerA = TestDataUtil.createTestOwnerA();
        Owner savedOwnerA = ownerDao.save(ownerA);
        PetCategory petCategoryA = TestDataUtil.createTestPetCategoryA();
        PetCategory savedPetCategoryA = petCategoryDao.save(petCategoryA);
        Pet petA = TestDataUtil.createTestPetA(savedPetCategoryA);
        Pet savedPetA = petDao.save(petA);
        Adoption adoptionA = TestDataUtil.createTestAdoptionA(savedOwnerA, savedPetA);
        underTest.save(adoptionA);

        Owner ownerB = TestDataUtil.createTestOwnerB();
        Owner savedOwnerB = ownerDao.save(ownerB);
        PetCategory petCategoryB = TestDataUtil.createTestPetCategoryB();
        PetCategory savedPetCategoryB = petCategoryDao.save(petCategoryB);
        Pet petB = TestDataUtil.createTestPetB(savedPetCategoryB);
        Pet savedPetB = petDao.save(petB);
        Adoption adoptionB = TestDataUtil.createTestAdoptionB(savedOwnerB, savedPetB);
        underTest.save(adoptionB);

        Owner ownerC = TestDataUtil.createTestOwnerC();
        Owner savedOwnerC = ownerDao.save(ownerC);
        PetCategory petCategoryC = TestDataUtil.createTestPetCategoryC();
        PetCategory savedPetCategoryC = petCategoryDao.save(petCategoryC);
        Pet petC = TestDataUtil.createTestPetC(savedPetCategoryC);
        Pet savedPetC = petDao.save(petC);
        Adoption adoptionC = TestDataUtil.createTestAdoptionC(savedOwnerC, savedPetC);
        underTest.save(adoptionC);

        Iterable<Adoption> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(adoptionA, adoptionB, adoptionC);
    }

    @Test
    public void testThatAdoptionCanBeUpdated() {
        Owner ownerA = TestDataUtil.createTestOwnerA();
        Owner savedOwner = ownerDao.save(ownerA);

        PetCategory petCategoryA = TestDataUtil.createTestPetCategoryA();
        PetCategory savedPetCategoryA = petCategoryDao.save(petCategoryA);
        PetCategory petCategoryB = TestDataUtil.createTestPetCategoryB();
        PetCategory savedPetCategoryB = petCategoryDao.save(petCategoryB);

        Pet petA = TestDataUtil.createTestPetA(savedPetCategoryA);
        Pet savedPetA = petDao.save(petA);
        Pet petB = TestDataUtil.createTestPetB(savedPetCategoryB);
        Pet savedPetB = petDao.save(petB);

        Adoption adoptionA = TestDataUtil.createTestAdoptionA(savedOwner, savedPetA);
        underTest.save(adoptionA);
        adoptionA.setPet(savedPetB);
        Adoption savedAdoption = underTest.save(adoptionA);

        Optional<Adoption> result = underTest.findById(savedAdoption.getAdoptionId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(adoptionA);
    }

    @Test
    public void testThatAdoptionCanBeDeleted() {
        Owner ownerA = TestDataUtil.createTestOwnerA();
        Owner savedOwner = ownerDao.save(ownerA);

        PetCategory petCategoryA = TestDataUtil.createTestPetCategoryA();
        PetCategory savedPetCategory = petCategoryDao.save(petCategoryA);

        Pet petA = TestDataUtil.createTestPetA(savedPetCategory);
        Pet savedPet = petDao.save(petA);

        Adoption adoptionA = TestDataUtil.createTestAdoptionA(savedOwner, savedPet);
        Adoption savedAdoption = underTest.save(adoptionA);

        underTest.deleteById(savedAdoption.getAdoptionId());
        Optional<Adoption> result = underTest.findById(savedAdoption.getAdoptionId());
        assertThat(result).isEmpty();
    }
}
