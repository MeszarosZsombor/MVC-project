package com.example.mvc_project.dao.impl;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.Adoption;
import com.example.mvc_project.domain.Owner;
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
public class AdoptionDaoImplIntegrationTests {

    private AdoptionDaoImpl underTest;
    private PetCategoryDaoImpl petCategoryDao;
    private PetDaoImpl petDao;
    private OwnerDaoImpl ownerDao;

    @Autowired
    public AdoptionDaoImplIntegrationTests(AdoptionDaoImpl underTest, PetCategoryDaoImpl petCategoryDao,
                                           PetDaoImpl petDao, OwnerDaoImpl ownerDao){
        this.underTest = underTest;
        this.petCategoryDao = petCategoryDao;
        this.petDao = petDao;
        this.ownerDao = ownerDao;
    }

    @Test
    public void testThatAdoptionCanBeCreatedAndRecalled() {
        Owner owner = TestDataUtil.createTestOwner();
        ownerDao.create(owner);

        PetCategory petCategory = TestDataUtil.createTestPetCategory();
        petCategoryDao.create(petCategory);

        Pet pet = TestDataUtil.createTestPet();
        petDao.create(pet);

        Adoption adoption = TestDataUtil.createTestAdoption();
        underTest.create(adoption);
        Optional<Adoption> result = underTest.findOne(adoption.getAdoptionId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(adoption);
    }
}
