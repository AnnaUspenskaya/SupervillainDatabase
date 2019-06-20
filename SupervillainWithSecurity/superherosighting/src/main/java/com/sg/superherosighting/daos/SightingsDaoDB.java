/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.daos;

import com.sg.superherosighting.daos.LocationDaoDB.LocationMapper;
import com.sg.superherosighting.daos.SupervillainsDaoDB.VillainMapper;
import com.sg.superherosighting.dtos.Location;
import com.sg.superherosighting.dtos.Sightings;
import com.sg.superherosighting.dtos.Supervillains;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.apache.catalina.mapper.Mapper;
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
public class SightingsDaoDB implements SightingsDao {

    @Autowired
    private JdbcTemplate jdbc;
    /*works*/
    private final String addSighting = "INSERT INTO Sighting (date, locationId) VALUES(?,?)";
    /*works*/
    private final String viewSighting = "SELECT * FROM Sighting where sightingId =?";
    /*works*/
    private final String viewAllSighting = "SELECT * FROM Sighting";
    /*works*/
    private final String updateSighting = "UPDATE Sighting SET date=?, locationId =? WHERE sightingId=?;";
    /*works*/
    private final String deleteSighting = "DELETE FROM Sighting  WHERE sightingId = ?";
    /*works*/
    private final String viewAllSightingsByDate = "SELECT * FROM Sighting where date=?";
    
    private final String viewAllVillainsBySighitng ="SELECT * FROM SuperVillain INNER JOIN SuperVillainSighting on SuperVillain.villainId=SuperVillainSighting.villainId where sightingId=?";
    
    private final String newsfeed= "select * from Sighting order by sightingId desc limit 10";

    @Override
    public Sightings addSighting(Sightings s) {
        jdbc.update(addSighting,
                s.getDate(),
                s.getLocation().getLocationId());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        s.setSightingId(newId);
             for(Supervillains sv:s.getSuperVillain()){
                 addSupevillainToSighting(sv.getVillainId(), s.getSightingId());
             }
        return s;
   
    }
private void addSupevillainToSighting(int villainId, int sightingId){
     String bridge ="insert into SuperVillainSighting (villainId, sightingId) values (?,?)";
     jdbc.update(bridge,
             villainId,
             sightingId);
}
    @Override
//            try{
//Supervillains sv = jdbc.queryForObject(getVillainById, new VillainMapper(), villainID);
//    sv.setOrganizationList(getAllOrgansByVillain(villainID));
//    sv.setSuperPower(getPowerForVillain(villainID));
//    sv.setSightingList(getAllSighitngsByVillain(villainID));
//    return sv;
//        }catch(DataAccessException ex){
//            return null;
//        }
    public Sightings viewSighting(int sightingId) {
        try {
            Sightings s = jdbc.queryForObject(viewSighting, new SightingMapper(), sightingId);
            s.setLocation(getLocationForSighting(sightingId));
            s.setSuperVillain(viewAllVillainsBySighitng(s));
            return s;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Location getLocationForSighting(int id) {
        final String selectLocationSighting = "Select * from Location join Sighting on Sighting.locationId=Location.locationId where Sighting.sightingId=?";
        return jdbc.queryForObject(selectLocationSighting, new LocationMapper(), id);
    }

    @Override
    public List<Sightings> viewAllSighting() {
        List<Sightings> sightings = jdbc.query(viewAllSighting, new SightingMapper());
        locationsForSightings(sightings);
        return sightings;
    }

    private void locationsForSightings(List<Sightings> sightings) {
        for (Sightings s : sightings) {
            s.setLocation(getLocationForSighting(s.getSightingId()));
        }
    }

    @Override
    public List<Sightings> viewAllSightingsByDate(LocalDate date) {
        List<Sightings> sightings = jdbc.query(viewAllSightingsByDate, new SightingMapper(), date);
        return sightings;
    }

    @Override
    public void updateSighting(Sightings s) {
        jdbc.update(updateSighting,
                s.getDate(),
                s.getSightingId(),
                s.getLocation().getLocationId());
    }

    @Override
    @Transactional
    public void deleteSighting(int sightingId) {
        final String deleteSuperVillainSighting = "delete from SuperVillainSighting where sightingId=?";
        jdbc.update(deleteSuperVillainSighting, sightingId);
        jdbc.update(deleteSighting, sightingId);
    }

    @Override
    public List<Sightings> Newsfeed() {
                List<Sightings> sightings = jdbc.query(newsfeed, new SightingMapper());
        locationsForSightings(sightings);
        return sightings;
    }

    @Override
    public List<Supervillains> viewAllVillainsBySighitng(Sightings s) {
List<Supervillains> villains = jdbc.query(viewAllVillainsBySighitng, new VillainMapper(), s.getSightingId());
return villains;
    }

    public static final class SightingMapper implements RowMapper<Sightings> {

        @Override
        public Sightings mapRow(ResultSet rs, int i) throws SQLException {
            Sightings s = new Sightings();
            s.setSightingId(rs.getInt("sightingId"));
            s.setDate(LocalDate.parse(rs.getString("date")));
           //s.setLocation(getLocationForSighting("location"));
            return s;
        }

    }
}
