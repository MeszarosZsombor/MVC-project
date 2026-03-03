package com.example.mvc_project.dao.impl;

import com.example.mvc_project.dao.PetDao;
import com.example.mvc_project.domain.Pet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class PetDaoImpl implements PetDao {

    private final JdbcTemplate jdbcTemplate;

    public PetDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Pet pet) {
        jdbcTemplate.update(
                "INSERT INTO pets (pet_id, pet_name, weight, age, gender, pet_category_id, adopted) VALUES (?, ?, ?, ?, ?, ?, ?)",
                pet.getPetId(), pet.getPetName(), pet.getWeight(), pet.getAge(), pet.getGender(), pet.getPetCategoryId(), pet.getAdopted()
        );
    }

    @Override
    public Optional<Pet> findOne(long petId) {
        List<Pet> results = jdbcTemplate.query(
                "SELECT pet_id, pet_name, weight, age, gender, pet_category_id, adopted FROM pets WHERE pet_id = ? LIMIT 1",
                new PetRowMapper(), petId
        );

        return results.stream().findFirst();
    }

    public static class PetRowMapper implements RowMapper<Pet> {

        @Override
        public Pet mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Pet.builder()
                    .petId(rs.getLong("pet_id"))
                    .petName(rs.getString("pet_name"))
                    .weight(rs.getInt("weight"))
                    .age(rs.getInt("age"))
                    .gender(rs.getString("gender"))
                    .petCategoryId(rs.getLong("pet_category_id"))
                    .adopted(rs.getBoolean("adopted"))
                    .build();
        }
    }
}
