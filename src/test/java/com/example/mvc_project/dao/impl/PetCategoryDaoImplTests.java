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
        PetCategory petCategory = TestDataUtil.createTestPetCategoryA();

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

    @Test
    public void testThatFindAllGeneratesCorrectSql() {
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT * FROM pet_category"),
                ArgumentMatchers.<PetCategoryDaoImpl.PetCategoryRowMapper>any()
        );
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        PetCategory petCategory = TestDataUtil.createTestPetCategoryA();
        underTest.update(petCategory.getPetCategoryId(), petCategory);
        verify(jdbcTemplate).update(
                "UPDATE pet_category SET pet_category_id = ?, pet_type = ? WHERE pet_category_id = ?",
                1L, "cat", 1L
        );
    }
}
