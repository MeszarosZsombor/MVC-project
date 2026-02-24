package com.example.mvc_project.dao.impl;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.Owner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class OwnerDaoImplIntegrationTests {

    private OwnerDaoImpl underTest;

    @Autowired
    public OwnerDaoImplIntegrationTests(OwnerDaoImpl underTest){
        this.underTest = underTest;
    }

    @Test
    public void testThatOwnerCanBeCreatedAndRecalled() {
        Owner owner = TestDataUtil.createTestOwner();

        underTest.create(owner);
        Optional<Owner> result = underTest.findOne(owner.getOwnerId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(owner);
    }
}
