package com.example.mvc_project.dao.impl;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.Pet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PetDaoImplTests {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PetDaoImpl underTest;

    @Test
    public void testThatCreatePetGeneratesCorrectSql() {
        Pet pet = TestDataUtil.createTestPetA();

        underTest.create(pet);

        verify(jdbcTemplate).update(
                eq("INSERT INTO pets (pet_id, pet_name, weight, age, gender, pet_category_id, adopted) VALUES (?, ?, ?, ?, ?, ?, ?)"),
                eq(1L), eq("Gato"), eq(10), eq(3), eq("M"), eq(1L), eq(true)
        );
    }

    @Test
    public void testThatFindOneGeneratesCorrectSql() {
        underTest.findOne(1L);
        verify(jdbcTemplate).query(
                eq("SELECT pet_id, pet_name, weight, age, gender, pet_category_id, adopted FROM pets WHERE pet_id = ? LIMIT 1"),
                ArgumentMatchers.<PetDaoImpl.PetRowMapper>any(),
                eq(1L)
        );
    }

    @Test
    public void testThatFindAllGeneratesCorrectSql() {
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT pet_id, pet_name, weight, age, gender, pet_category_id, adopted FROM pets"),
                ArgumentMatchers.<PetDaoImpl.PetRowMapper>any()
        );
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        Pet pet = TestDataUtil.createTestPetA();
        underTest.update(pet.getPetId(), pet);

        verify(jdbcTemplate).update(
          "UPDATE pets SET pet_id = ?, pet_name = ?, weight = ?, age = ?, gender = ?, pet_category_id = ?, adopted = ? WHERE pet_id = ?",
          1L, "Gato", 10, 3, "M", 1L, true, 1L
        );
    }

    @Test
    public void testThatDeleteGeneratesCorrectSql() {
        underTest.delete(1L);

        verify(jdbcTemplate).update(
                "DELETE FROM pets WHERE pet_id = ?",
                1L
        );
    }
}
