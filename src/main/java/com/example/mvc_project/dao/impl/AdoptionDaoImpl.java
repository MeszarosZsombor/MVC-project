package com.example.mvc_project.dao.impl;

import com.example.mvc_project.dao.AdoptionDao;
import org.springframework.jdbc.core.JdbcTemplate;

public class AdoptionDaoImpl implements AdoptionDao {

    private final JdbcTemplate jdbcTemplate;

    public AdoptionDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
