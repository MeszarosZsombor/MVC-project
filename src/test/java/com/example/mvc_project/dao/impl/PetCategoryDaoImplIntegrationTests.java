package com.example.mvc_project.dao.impl;

import com.example.mvc_project.TestDataUtil;
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
public class PetCategoryDaoImplIntegrationTests {

    private PetCategoryDaoImpl underTest;

    @Autowired
    public PetCategoryDaoImplIntegrationTests(PetCategoryDaoImpl underTest){
        this.underTest = underTest;
    }

    @Test
    public void testThatPetCategoryCanBeCreatedAndRecalled(){
        PetCategory petCategory = TestDataUtil.createTestPetCategory();

        underTest.create(petCategory);
        Optional<PetCategory> result = underTest.findOne(petCategory.getPetCategoryId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(petCategory);
    }

}
