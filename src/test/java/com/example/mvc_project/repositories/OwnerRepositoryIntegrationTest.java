package com.example.mvc_project.repositories;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.Owner;
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
public class OwnerRepositoryIntegrationTest {

    private OwnerRepository underTest;

    @Autowired
    public OwnerRepositoryIntegrationTest(OwnerRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void testThatOwnerCanBeCreatedAndRecalled() {
        Owner owner = TestDataUtil.createTestOwnerA();

        Owner savedOwner = underTest.save(owner);
        Optional<Owner> result = underTest.findById(savedOwner.getOwnerId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(owner);
    }

    @Test
    public void testThatMultipleOwnersCanBeCreatedAndRecalled() {
        Owner ownerA = TestDataUtil.createTestOwnerA();
        underTest.save(ownerA);
        Owner ownerB = TestDataUtil.createTestOwnerB();
        underTest.save(ownerB);
        Owner ownerC = TestDataUtil.createTestOwnerC();
        underTest.save(ownerC);
        Owner ownerD = TestDataUtil.createTestOwnerD();
        underTest.save(ownerD);

        Iterable<Owner> result = underTest.findAll();
        assertThat(result)
                .hasSize(4)
                .containsExactly(ownerA, ownerB, ownerC, ownerD);
    }

    @Test
    public void testThatOwnerCanBeUpdated() {
        Owner ownerA = TestDataUtil.createTestOwnerA();
        underTest.save(ownerA);
        ownerA.setName("UPDATED");
        Owner savedOwner = underTest.save(ownerA);
        Optional<Owner> result = underTest.findById(savedOwner.getOwnerId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(ownerA);
    }

    @Test
    public void testThatOwnerCanBeDeleted() {
        Owner ownerA = TestDataUtil.createTestOwnerA();
        Owner savedOwner = underTest.save(ownerA);
        underTest.deleteById(savedOwner.getOwnerId());
        Optional<Owner> result = underTest.findById(savedOwner.getOwnerId());
        assertThat(result).isEmpty();
    }
}
