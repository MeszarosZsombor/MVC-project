package com.example.mvc_project.dao.impl;

import com.example.mvc_project.dao.PetDao;
import org.springframework.jdbc.core.JdbcTemplate;

public class PetDaoImpl implements PetDao {

    private final JdbcTemplate jdbcTemplate;

    public PetDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
