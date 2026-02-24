package com.example.mvc_project.dao.impl;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.Owner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OwnerDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private OwnerDaoImpl underTest;

    @Test
    public void testThatCreateOwnerGeneratesCorrectSql() {
        Owner owner = TestDataUtil.createTestOwner();

        underTest.create(owner);

        verify(jdbcTemplate).update(
                eq("INSERT INTO owners (owner_id, role, email, password, name) VALUES (?, ?, ?, ?, ?)"),
                eq(1L), eq("user"), eq("test@email.com"), eq("password"), eq("John Doe")
        );
    }

    @Test
    public void testThatFindOneGeneratesTheCorrectSql() {
        underTest.findOne(1L);
        verify(jdbcTemplate).query(
                eq("SELECT owner_id, role, email, password, name, created_at FROM owners WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<OwnerDaoImpl.OwnerRowMapper>any(),
                eq(1L)
        );
    }
}
