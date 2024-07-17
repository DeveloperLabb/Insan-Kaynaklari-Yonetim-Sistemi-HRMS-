package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository("mySQL")
public class mySQLDataAccessService implements PersonDao{

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public mySQLDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public int insertPerson(UUID id, Person person) {
        String sql = "INSERT INTO person (id, name) VALUES (?, ?)";
        return jdbcTemplate.update(sql, id.toString(), person.getName());
    }

    @Override
    public int insertPerson(Person person) {
        return PersonDao.super.insertPerson(person);
    }

    @Override
    public List<Person> getAllPerson() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Person> persons = new ArrayList<>();
        try {
            conn = jdbcTemplate.getDataSource().getConnection();
            ps = conn.prepareStatement("SELECT * FROM person");
            rs = ps.executeQuery();

            // ResultSet üzerinde döngü ile veri işleme örneği
            while (rs.next()) {
                // Her bir sütundan veri alma örneği
                String id = rs.getString("id");
                String name = rs.getString("name");
                Person person = new Person(id,name);
                persons.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching data from database", e);
        } finally {
            // ResultSet, PreparedStatement ve Connection kapatma
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException("Error closing ResultSet", e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException("Error closing PreparedStatement", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException("Error closing Connection", e);
                }
            }
        }
        return persons;
    }

    @Override
    public int deletePerson(UUID id) {
        String sql = "DELETE FROM person WHERE id = ?";
        return jdbcTemplate.update(sql, id.toString());
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        return 0;
    }

    @Override
    public Optional<Person> findPersonById(UUID id) {
        return Optional.empty();
    }
}
