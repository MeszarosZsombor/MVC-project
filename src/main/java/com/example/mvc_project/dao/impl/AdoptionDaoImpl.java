package com.example.mvc_project.dao.impl;

import com.example.mvc_project.dao.AdoptionDao;
import com.example.mvc_project.domain.Adoption;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class AdoptionDaoImpl implements AdoptionDao {

    private final JdbcTemplate jdbcTemplate;

    public AdoptionDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Adoption adoption) {
        jdbcTemplate.update(
                "INSERT INTO adoptions (adoption_id, owner_id, pet_id) VALUES (?, ?, ?)",
                adoption.getAdoptionId(), adoption.getOwnerId(), adoption.getPetId()
        );
    }

    @Override
    public Optional<Adoption> findOne(long adoptionId) {
        List<Adoption> results = jdbcTemplate.query(
                "SELECT adoption_id, owner_id, pet_id FROM adoptions WHERE adoption_id = ? LIMIT 1",
                new AdoptionRowMapper(), adoptionId
        );

        return results.stream().findFirst();
    }

    @Override
    public List<Adoption> find() {
        return jdbcTemplate.query(
                "SELECT adoption_id, owner_id, pet_id FROM adoptions",
                new AdoptionRowMapper()
        );
    }

    public static class AdoptionRowMapper implements RowMapper<Adoption> {

        @Override
        public Adoption mapRow(ResultSet rs, int rowNum) throws SQLException {
            return  Adoption.builder()
                    .adoptionId(rs.getLong("adoption_id"))
                    .ownerId(rs.getLong("owner_id"))
                    .petId(rs.getLong("pet_id"))
                    .build();
        }
    }
}
