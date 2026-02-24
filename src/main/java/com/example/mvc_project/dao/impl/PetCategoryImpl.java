package com.example.mvc_project.dao.impl;

import com.example.mvc_project.dao.PetCategoryDao;
import org.springframework.jdbc.core.JdbcTemplate;

public class PetCategoryImpl implements PetCategoryDao {

    private final JdbcTemplate jdbcTemplate;

    public PetCategoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
