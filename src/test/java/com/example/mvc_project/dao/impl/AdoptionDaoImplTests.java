package com.example.mvc_project.dao.impl;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.Adoption;
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
public class AdoptionDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AdoptionDaoImpl underTest;

    @Test
    public void testThatCreateAdoptionGeneratesCorrectSql() {
        Adoption adoption = TestDataUtil.createTestAdoptionA();

        underTest.create(adoption);

        verify(jdbcTemplate).update(
                eq("INSERT INTO adoptions (adoption_id, owner_id, pet_id) VALUES (?, ?, ?)"),
                eq(1L), eq(1L), eq(2L)
        );
    }

    @Test
    public void testThatFindOneGeneratesCorrectSql() {
        underTest.findOne(1L);
        verify(jdbcTemplate).query(
                eq("SELECT adoption_id, owner_id, pet_id FROM adoptions WHERE adoption_id = ? LIMIT 1"),
                ArgumentMatchers.<AdoptionDaoImpl.AdoptionRowMapper>any(),
                eq(1L)
        );
    }

    @Test
    public void testThatFindAllGeneratesCorrectSql() {
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT adoption_id, owner_id, pet_id FROM adoptions"),
                ArgumentMatchers.<AdoptionDaoImpl.AdoptionRowMapper>any()
        );
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        Adoption adoption = TestDataUtil.createTestAdoptionA();
        underTest.update(adoption.getAdoptionId(), adoption);

        verify(jdbcTemplate).update(
                "UPDATE adoptions SET adoption_id = ?, owner_id = ?, pet_id = ? WHERE adoption_id = ?",
                1L, 1L, 2L, 1L
        );
    }

    @Test
    public void testThatDeleteGeneratesCorrectSql() {
        underTest.delete(1L);

        verify(jdbcTemplate).update(
                "DELETE FROM adoptions WHERE adoption_id = ?",
                1L
        );
    }
}
