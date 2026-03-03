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
        Adoption adoption = TestDataUtil.createTestAdoption();

        underTest.create(adoption);

        verify(jdbcTemplate).update(
                eq("INSERT INTO adoptions (adoption_id, owner_id, pet_id) VALUES (?, ?, ?)"),
                eq(2L), eq(1L), eq(2L)
        );
    }

    @Test
    public void testThatFindOneGeneratesCorrectSql() {
        underTest.findOne(2L);
        verify(jdbcTemplate).query(
                eq("SELECT adoption_id, owner_id, pet_id FROM adoptions WHERE adoption_id = ? LIMIT 1"),
                ArgumentMatchers.<AdoptionDaoImpl.AdoptionRowMapper>any(),
                eq(2L)
        );
    }
}
