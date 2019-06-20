/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.daos;

import com.sg.superherosighting.dtos.Superpowers;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author anna
 */
@Repository
public class SuperpowersDaoDB implements SuperpowersDao {

    @Autowired
    private JdbcTemplate jdbc;
    /*works*/
    private final String createPower = "INSERT INTO SuperPower (name, description) VALUES(?,?)";
    /*works*/
    private final String getPower = "SELECT * FROM SuperPower where powerId =?";
    /*works*/
    private final String getAllPowers = "SELECT * FROM SuperPower";
    /*works*/
    private final String updatePower = "UPDATE SuperPower SET name=?, description=? WHERE powerId=?";
    /*works*/
    private final String deletePower = "DELETE FROM SuperPower WHERE powerId=?";

    @Override
    @Transactional
    public Superpowers createPower(Superpowers sp) {
        jdbc.update(createPower,
                sp.getName(),
                sp.getDescription());
                int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
sp.setPowerId(newId);
return sp;
                
    }

    @Override
    public Superpowers getPower(int powerId) {
try{
    return jdbc.queryForObject(getPower, new PowerMapper(), powerId);
}catch(DataAccessException ex){
    return null;
}
    }

    @Override
    public List<Superpowers> getAllPowers() {
return jdbc.query(getAllPowers, new PowerMapper());
    }

    @Override
    public void updatePower(Superpowers sp) {
        jdbc.update(updatePower,
                sp.getName(),
                sp.getDescription(),
                sp.getPowerId());
    }

    @Override
    @Transactional
    public void deletePower(int powerId) {
        final String deletePowerVillain = "delete from SuperVillain where powerId = ?";
        jdbc.update(deletePowerVillain, powerId);
        jdbc.update(deletePower, powerId);
    }

    public static final class PowerMapper implements RowMapper<Superpowers> {

        @Override
        public Superpowers mapRow(ResultSet rs, int i) throws SQLException {
            Superpowers sp = new Superpowers();
            sp.setPowerId(rs.getInt("powerId"));
            sp.setName(rs.getString("name"));
            sp.setDescription(rs.getString("description"));

            return sp;
        }
    }

}
