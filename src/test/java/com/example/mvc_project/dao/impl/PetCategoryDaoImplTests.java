package com.example.mvc_project.dao.impl;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.PetCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PetCategoryDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PetCategoryDaoImpl underTest;

    @Test
    public void testThatCreatePetCategoryGeneratesCorrectSql() {
        PetCategory petCategory = TestDataUtil.createTestPetCategory();

        underTest.create(petCategory);

        verify(jdbcTemplate).update(
                eq("INSERT INTO pet_category (pet_category_id, pet_type) VALUES (?, ?)"),
                eq(1L), eq("cat")
        );
    }

    @Test
    public void testThatFindOneGeneratesCorrectSql() {
        underTest.findOne(1L);
        verify(jdbcTemplate).query(
                eq("SELECT pet_category_id, pet_type FROM pet_category WHERE pet_category_id = ? LIMIT 1"),
                ArgumentMatchers.<PetCategoryDaoImpl.PetCategoryRowMapper>any(),
                eq(1L)
        );
    }
}
