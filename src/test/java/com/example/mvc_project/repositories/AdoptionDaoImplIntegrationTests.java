//package com.example.mvc_project.repositories;
//
//import com.example.mvc_project.TestDataUtil;
//import com.example.mvc_project.domain.Adoption;
//import com.example.mvc_project.domain.Owner;
//import com.example.mvc_project.domain.Pet;
//import com.example.mvc_project.domain.PetCategory;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class AdoptionDaoImplIntegrationTests {
//
//    private AdoptionDaoImpl underTest;
//    private PetCategoryDaoImpl petCategoryDao;
//    private PetDaoImpl petDao;
//    private OwnerDaoImpl ownerDao;
//
//    @Autowired
//    public AdoptionDaoImplIntegrationTests(AdoptionDaoImpl underTest, PetCategoryDaoImpl petCategoryDao,
//                                           PetDaoImpl petDao, OwnerDaoImpl ownerDao){
//        this.underTest = underTest;
//        this.petCategoryDao = petCategoryDao;
//        this.petDao = petDao;
//        this.ownerDao = ownerDao;
//    }
//
//    @Test
//    public void testThatAdoptionCanBeCreatedAndRecalled() {
//        Owner owner = TestDataUtil.createTestOwnerA();
//        ownerDao.create(owner);
//
//        PetCategory petCategory = TestDataUtil.createTestPetCategoryA();
//        petCategoryDao.create(petCategory);
//
//        Pet pet = TestDataUtil.createTestPetA();
//        pet.setPetCategoryId(petCategory.getPetCategoryId());
//        petDao.create(pet);
//
//        Adoption adoption = TestDataUtil.createTestAdoptionA();
//        adoption.setOwnerId(owner.getOwnerId());
//        adoption.setPetId(pet.getPetId());
//        underTest.create(adoption);
//        Optional<Adoption> result = underTest.findOne(adoption.getAdoptionId());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(adoption);
//    }
//
//    @Test
//    public void testThatMultipleAdoptionsCanBeCreatedAndRecalled() {
//        Owner ownerA = TestDataUtil.createTestOwnerA();
//        ownerDao.create(ownerA);
//        PetCategory petCategoryA = TestDataUtil.createTestPetCategoryA();
//        petCategoryDao.create(petCategoryA);
//        Pet petA = TestDataUtil.createTestPetA();
//        petA.setPetCategoryId(petCategoryA.getPetCategoryId());
//        petDao.create(petA);
//        Adoption adoptionA = TestDataUtil.createTestAdoptionA();
//        adoptionA.setOwnerId(ownerA.getOwnerId());
//        adoptionA.setPetId(petA.getPetId());
//        underTest.create(adoptionA);
//
//        Owner ownerB = TestDataUtil.createTestOwnerB();
//        ownerDao.create(ownerB);
//        PetCategory petCategoryB = TestDataUtil.createTestPetCategoryB();
//        petCategoryDao.create(petCategoryB);
//        Pet petB = TestDataUtil.createTestPetB();
//        petB.setPetCategoryId(petCategoryB.getPetCategoryId());
//        petDao.create(petB);
//        Adoption adoptionB = TestDataUtil.createTestAdoptionB();
//        adoptionB.setOwnerId(ownerA.getOwnerId());
//        adoptionB.setPetId(petA.getPetId());
//        underTest.create(adoptionB);
//
//        Owner ownerC = TestDataUtil.createTestOwnerC();
//        ownerDao.create(ownerC);
//        PetCategory petCategoryC = TestDataUtil.createTestPetCategoryC();
//        petCategoryDao.create(petCategoryC);
//        Pet petC = TestDataUtil.createTestPetC();
//        petC.setPetCategoryId(petCategoryC.getPetCategoryId());
//        petDao.create(petC);
//        Adoption adoptionC = TestDataUtil.createTestAdoptionC();
//        adoptionC.setOwnerId(ownerA.getOwnerId());
//        adoptionC.setPetId(petA.getPetId());
//        underTest.create(adoptionC);
//
//        List<Adoption> result = underTest.find();
//        assertThat(result)
//                .hasSize(3)
//                .containsExactly(adoptionA, adoptionB, adoptionC);
//    }
//
//    @Test
//    public void testThatAdoptionCanBeUpdated() {
//        Owner ownerA = TestDataUtil.createTestOwnerA();
//        ownerDao.create(ownerA);
//
//        PetCategory petCategoryA = TestDataUtil.createTestPetCategoryA();
//        petCategoryDao.create(petCategoryA);
//        PetCategory petCategoryB = TestDataUtil.createTestPetCategoryB();
//        petCategoryDao.create(petCategoryB);
//
//        Pet petA = TestDataUtil.createTestPetA();
//        petA.setPetCategoryId(petCategoryA.getPetCategoryId());
//        petDao.create(petA);
//        Pet petB = TestDataUtil.createTestPetB();
//        petB.setPetCategoryId(petCategoryB.getPetCategoryId());
//        petDao.create(petB);
//
//        Adoption adoptionA = TestDataUtil.createTestAdoptionA();
//        adoptionA.setOwnerId(ownerA.getOwnerId());
//        adoptionA.setPetId(petA.getPetId());
//
//        underTest.create(adoptionA);
//        adoptionA.setPetId(petB.getPetId());
//        underTest.update(adoptionA.getAdoptionId(), adoptionA);
//        Optional<Adoption> result = underTest.findOne(adoptionA.getAdoptionId());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(adoptionA);
//    }
//
//    @Test
//    public void testThatAdoptionCanBeDeleted() {
//        Owner ownerA = TestDataUtil.createTestOwnerA();
//        ownerDao.create(ownerA);
//
//        PetCategory petCategoryA = TestDataUtil.createTestPetCategoryA();
//        petCategoryDao.create(petCategoryA);
//
//        Pet petA = TestDataUtil.createTestPetA();
//        petA.setPetCategoryId(petCategoryA.getPetCategoryId());
//        petDao.create(petA);
//
//        Adoption adoptionA = TestDataUtil.createTestAdoptionA();
//        adoptionA.setOwnerId(ownerA.getOwnerId());
//        adoptionA.setPetId(petA.getPetId());
//        underTest.create(adoptionA);
//
//        underTest.delete(adoptionA.getAdoptionId());
//        Optional<Adoption> result = underTest.findOne(adoptionA.getAdoptionId());
//        assertThat(result).isEmpty();
//    }
//}
