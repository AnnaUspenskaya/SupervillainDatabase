/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.daos;

import com.sg.superherosighting.daos.SupervillainsDaoDB.VillainMapper;
import com.sg.superherosighting.dtos.Location;
import com.sg.superherosighting.dtos.Supervillains;
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
public class LocationDaoDB implements LocationDao{
    @Autowired
    private JdbcTemplate jdbc;
    /*works*/private final String createLocation = "INSERT INTO Location (name, description, streetName, city, state, zipCode, longitude, latitude) VALUES(?,?,?,?,?,?,?,?)";
    /*works*/private final String getLocationById = "SELECT * FROM Location where locationId =?";
    /*works*/private final String getAllLocations = "SELECT * FROM Location";
    /*works*/private final String updateLocation = "UPDATE Location SET name=?, description =?, streetName=?, city=?, state=?, zipCode=?, longitude=?, latitude=? WHERE locationId=? ;";
    /*works*/private final String deleteLocation = "DELETE FROM Location WHERE locationId = ?";
    private final String getAllVillainsByLocation = "select villainId from SuperVillainSighting inner join Sighting where SuperVillainSighting.sightingId=Sighting.sightingId and Sighting.locationId=?;";

    @Override
    @Transactional
    public Location createLocation(Location location) {
jdbc.update(createLocation, 
        location.getName(),
        location.getDescription(),
        location.getStreet(),
        location.getCity(),
        location.getState(),
        location.getZipCode(),
        location.getLongitude(),
        location.getLatitude()); 
    
    int newId = jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
    
    location.setLocationId(newId);
           return location;
    }

    @Override
    public Location getLocationById(int locationId) {
try {
return jdbc.queryForObject(getLocationById, new LocationMapper(), locationId);
}catch(DataAccessException ex){
    return null;
}
    }

    @Override
    public List<Location> getAllLocations() {
return jdbc.query(getAllLocations, new LocationMapper());    }

    @Override
    public void updateLocation(Location location) {
        jdbc.update(updateLocation,
                location.getName(),
                location.getDescription(),
                location.getStreet(),
                location.getCity(),
                location.getState(),
                location.getZipCode(),
                location.getLongitude(),
                location.getLatitude(),
                location.getLocationId());
    }

    @Override
    @Transactional 
    public void deleteLocation(int locationId) {
final String deleteOrgan="delete from Organization where locationId=?";
   final String deleteSighting="delete from Sighting where locationId=?";
           jdbc.update(deleteOrgan, locationId);
           jdbc.update(deleteSighting, locationId);
           jdbc.update(deleteLocation, locationId);
        }
    public List<Supervillains> getAllVillainsByLocation (int locationId){
        return jdbc.query(getAllVillainsByLocation, new VillainMapper(), locationId);
    }
    public static final class LocationMapper implements RowMapper<Location>{

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
Location location= new Location();
location.setLocationId(rs.getInt("locationId"));
location.setName(rs.getString("name"));
location.setDescription(rs.getString("description"));
location.setStreet(rs.getString("streetName"));
location.setCity(rs.getString("city"));
location.setState(rs.getString("state"));
location.setZipCode(rs.getString("zipCode"));
location.setLongitude(rs.getString("longitude"));
location.setLatitude(rs.getString("latitude"));
return location;

        }
        
        
    }
}
