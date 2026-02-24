package com.example.mvc_project.dao.impl;

import com.example.mvc_project.dao.OwnerDao;
import com.example.mvc_project.domain.Owner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class OwnerDaoImpl implements OwnerDao {

    private final JdbcTemplate jdbcTemplate;

    public OwnerDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Owner owner) {
        jdbcTemplate.update(
                "INSERT INTO owners (owner_id, role, email, password, name) VALUES (?, ?, ?, ?, ?)",
                    owner.getOwnerId(), owner.getRole(), owner.getEmail(), owner.getPassword(), owner.getName());
    }

    @Override
    public Optional<Owner> findOne(long ownerId) {
        List<Owner> results = jdbcTemplate.query(
                "SELECT owner_id, role, email, password, name, created_at FROM owners WHERE owner_id = ? LIMIT 1",
                new OwnerRowMapper(), ownerId
        );

        return results.stream().findFirst();
    }

    public static class OwnerRowMapper implements RowMapper<Owner> {

        @Override
        public Owner mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Owner.builder()
                    .ownerId(rs.getLong("owner_id"))
                    .role(rs.getString("role"))
                    .email(rs.getString("email"))
                    .password(rs.getString("password"))
                    .name(rs.getString("name"))
                    .build();
        }

    }
}
