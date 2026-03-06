//package com.example.mvc_project.repositories;
//
//import com.example.mvc_project.TestDataUtil;
//import com.example.mvc_project.domain.Owner;
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
//public class OwnerDaoImplIntegrationTests {
//
//    private OwnerDaoImpl underTest;
//
//    @Autowired
//    public OwnerDaoImplIntegrationTests(OwnerDaoImpl underTest){
//        this.underTest = underTest;
//    }
//
//    @Test
//    public void testThatOwnerCanBeCreatedAndRecalled() {
//        Owner owner = TestDataUtil.createTestOwnerA();
//
//        underTest.create(owner);
//        Optional<Owner> result = underTest.findOne(owner.getOwnerId());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(owner);
//    }
//
//    @Test
//    public void testThatMultipleOwnersCanBeCreatedAndRecalled() {
//        Owner ownerA = TestDataUtil.createTestOwnerA();
//        underTest.create(ownerA);
//        Owner ownerB = TestDataUtil.createTestOwnerB();
//        underTest.create(ownerB);
//        Owner ownerC = TestDataUtil.createTestOwnerC();
//        underTest.create(ownerC);
//        Owner ownerD = TestDataUtil.createTestOwnerD();
//        underTest.create(ownerD);
//
//        List<Owner> result = underTest.find();
//        assertThat(result)
//                .hasSize(4)
//                .containsExactly(ownerA, ownerB, ownerC, ownerD);
//    }
//
//    @Test
//    public void testThatOwnerCanBeUpdated() {
//        Owner ownerA = TestDataUtil.createTestOwnerA();
//        underTest.create(ownerA);
//        ownerA.setName("UPDATED");
//        underTest.update(ownerA.getOwnerId(), ownerA);
//        Optional<Owner> result = underTest.findOne(ownerA.getOwnerId());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(ownerA);
//    }
//
//    @Test
//    public void testThatOwnerCanBeDeleted() {
//        Owner ownerA = TestDataUtil.createTestOwnerA();
//        underTest.create(ownerA);
//        underTest.delete(ownerA.getOwnerId());
//        Optional<Owner> result = underTest.findOne(ownerA.getOwnerId());
//        assertThat(result).isEmpty();
//    }
//}
