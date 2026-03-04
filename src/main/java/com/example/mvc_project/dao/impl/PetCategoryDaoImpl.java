package com.example.mvc_project.dao.impl;

import com.example.mvc_project.dao.PetCategoryDao;
import com.example.mvc_project.domain.PetCategory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class PetCategoryDaoImpl implements PetCategoryDao {

    private final JdbcTemplate jdbcTemplate;

    public PetCategoryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void create(PetCategory petCategory) {
        jdbcTemplate.update(
                "INSERT INTO pet_category (pet_category_id, pet_type) VALUES (?, ?)",
                petCategory.getPetCategoryId(), petCategory.getPetType()
        );
    }

    @Override
    public Optional<PetCategory> findOne(long petCategoryId) {
        List<PetCategory> results = jdbcTemplate.query(
                "SELECT pet_category_id, pet_type FROM pet_category WHERE pet_category_id = ? LIMIT 1",
                new PetCategoryRowMapper(), petCategoryId
        );

        return results.stream().findFirst();
    }

    @Override
    public List<PetCategory> find() {
        return jdbcTemplate.query(
                "SELECT * FROM pet_category",
                new PetCategoryRowMapper()
        );
    }

    @Override
    public void update(Long petCategoryId, PetCategory petCategory) {
        jdbcTemplate.update(
                "UPDATE pet_category SET pet_category_id = ?, pet_type = ? WHERE pet_category_id = ?",
                petCategory.getPetCategoryId(), petCategory.getPetType(), petCategoryId
        );
    }

    public static class PetCategoryRowMapper implements RowMapper<PetCategory> {

        @Override
        public PetCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
            return PetCategory.builder()
                    .petCategoryId(rs.getLong("pet_category_id"))
                    .petType(rs.getString("pet_type"))
                    .build();
        }
    }
}
