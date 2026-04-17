package com.example.mvc_project.repositories;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.entities.OwnerEntity;
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
public class OwnerEntityRepositoryIntegrationTest {

    private OwnerRepository underTest;

    @Autowired
    public OwnerEntityRepositoryIntegrationTest(OwnerRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void testThatOwnerCanBeCreatedAndRecalled() {
        OwnerEntity ownerEntity = TestDataUtil.createTestOwnerA();

        OwnerEntity savedOwnerEntity = underTest.save(ownerEntity);
        Optional<OwnerEntity> result = underTest.findById(savedOwnerEntity.getOwnerId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(ownerEntity);
    }

    @Test
    public void testThatMultipleOwnersCanBeCreatedAndRecalled() {
        OwnerEntity ownerEntityA = TestDataUtil.createTestOwnerA();
        underTest.save(ownerEntityA);
        OwnerEntity ownerEntityB = TestDataUtil.createTestOwnerB();
        underTest.save(ownerEntityB);
        OwnerEntity ownerEntityC = TestDataUtil.createTestOwnerC();
        underTest.save(ownerEntityC);
        OwnerEntity ownerEntityD = TestDataUtil.createTestOwnerD();
        underTest.save(ownerEntityD);

        Iterable<OwnerEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(4)
                .containsExactly(ownerEntityA, ownerEntityB, ownerEntityC, ownerEntityD);
    }

    @Test
    public void testThatOwnerCanBeUpdated() {
        OwnerEntity ownerEntityA = TestDataUtil.createTestOwnerA();
        underTest.save(ownerEntityA);
        ownerEntityA.setName("UPDATED");
        OwnerEntity savedOwnerEntity = underTest.save(ownerEntityA);
        Optional<OwnerEntity> result = underTest.findById(savedOwnerEntity.getOwnerId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(ownerEntityA);
    }

    @Test
    public void testThatOwnerCanBeDeleted() {
        OwnerEntity ownerEntityA = TestDataUtil.createTestOwnerA();
        OwnerEntity savedOwnerEntity = underTest.save(ownerEntityA);
        underTest.deleteById(savedOwnerEntity.getOwnerId());
        Optional<OwnerEntity> result = underTest.findById(savedOwnerEntity.getOwnerId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetOwnersWithRoleUser() {
        OwnerEntity ownerEntityA = underTest.save(TestDataUtil.createTestOwnerA());
        OwnerEntity ownerEntityB = underTest.save(TestDataUtil.createTestOwnerB());
        OwnerEntity ownerEntityC = underTest.save(TestDataUtil.createTestOwnerC());
        OwnerEntity ownerEntityD = underTest.save(TestDataUtil.createTestOwnerD());

        Iterable<OwnerEntity> result = underTest.roleIs("user");
        assertThat(result).containsExactly(ownerEntityA, ownerEntityB, ownerEntityC, ownerEntityD);
    }

    @Test
    public void testThatExistsByEmailMethodIsWorking() {
        OwnerEntity ownerEntityA = TestDataUtil.createTestOwnerA();
        underTest.save(ownerEntityA);

        assertThat(underTest.existsByEmail(ownerEntityA.getEmail())).isTrue();
    }

    @Test
    public void testThatExistsByEmailMethodGivesBackFalseWithNonRegisteredEmail() {
        OwnerEntity ownerEntityA = TestDataUtil.createTestOwnerA();
        underTest.save(ownerEntityA);

        OwnerEntity ownerEntityB = TestDataUtil.createTestOwnerB();

        assertThat(underTest.existsByEmail(ownerEntityB.getEmail())).isFalse();
    }
}
