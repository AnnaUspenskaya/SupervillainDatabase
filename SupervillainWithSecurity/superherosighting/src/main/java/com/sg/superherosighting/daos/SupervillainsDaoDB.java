/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.daos;

import com.sg.superherosighting.daos.SuperpowersDaoDB.PowerMapper;
import com.sg.superherosighting.daos.SightingsDaoDB.SightingMapper;
import com.sg.superherosighting.dtos.Location;
import com.sg.superherosighting.dtos.Organizations;
import com.sg.superherosighting.dtos.Sightings;
import com.sg.superherosighting.dtos.Superpowers;
import com.sg.superherosighting.dtos.Supervillains;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class SupervillainsDaoDB implements SupervillainsDao {

    @Autowired
    private JdbcTemplate jdbc;

    /*works*/
    private final String createVillain = "INSERT INTO SuperVillain (name, description, powerId) VALUES(?,?,?)";
    /*works*/
    private final String getVillainById = "SELECT * FROM SuperVillain where villainId =?";
    /*works*/
    private final String getAllVillains = "SELECT * FROM SuperVillain";
    /*works*/
    private final String updateVillain = "UPDATE SuperVillain SET name=?, description =?, powerId=? WHERE villainId=?";
    /*works*/
    private final String deleteVillain = "DELETE FROM SuperVillain WHERE villainId = ?";
//many to many 
    /*works*/
    private final String getAllVillainsByOrgan = "SELECT * FROM SuperVillain INNER JOIN OrganizationVillain on "
            + "SuperVillain.villainId=OrganizationVillain.villainId "
            + "where organizationId=?";
    private final String getAllVillainsByLocation = "select villainId from SuperVillainSighting inner join Sighting where SuperVillainSighting.sightingId=Sighting.sightingId and Sighting.locationId=?;";
//many to many 
    /*works*/
    private final String getAllOrgansByVillain = "SELECT * FROM Organization INNER JOIN OrganizationVillain on "
            + "Organization.organizationId=OrganizationVillain.organizationId "
            + "where villainId=?";
    private final String getAllSighitngsByVillain = "SELECT * FROM Sighting INNER JOIN SuperVillainSighting on "
            + "Sighting.sightingId = SuperVillainSighting.sightingId where villainId= ?";
    private final String getPowerForVillain = "SELECT p.* FROM SuperPower p JOIN SuperVillain s ON s.powerId = p.powerId WHERE s.villainId = ?";

    @Override
    @Transactional
    public Supervillains createVillain(Supervillains sv) {
        jdbc.update(createVillain,
                sv.getName(),
                sv.getDescription(),
                sv.getSuperPower().getPowerId()
        );
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sv.setVillainId(newId);
        for (Organizations o : sv.getOrganizationList()) {
            insertVillainOrganization(o.getOrganizationId(), sv.getVillainId());
        }
        return sv;
    }

    @Override
    public Supervillains getVillainById(int villainID) {
        try {
            Supervillains sv = jdbc.queryForObject(getVillainById, new VillainMapper(), villainID);
            sv.setOrganizationList(getAllOrgansByVillain(villainID));
            sv.setSuperPower(getPowerForVillain(villainID));
            sv.setSightingList(getAllSighitngsByVillain(villainID));
            return sv;
        } catch (DataAccessException ex) {
            Logger.getLogger(SupervillainsDaoDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    //private static final Logger LOG = Logger.getLogger(SupervillainsDaoDB.class.getName());
    private void insertVillainOrganization(int organId, int villainId) {
        final String insertOrganizationVillain = "INSERT INTO OrganizationVillain (villainId, organizationId) values (?,?)";
        Supervillains sv = getVillainById(villainId);
        if (sv.getOrganizationList() != null) {
 jdbc.update(insertOrganizationVillain,
                    villainId,
                    organId);
        }
    }
//    private void insertVillainSighting(Supervillains sv){
//        final String insertSuperVillainSighting = "Insert into SuperVillainSighting (sightingId, villainId) values (?, ?) ";
//        for (Sightings s:sv.getSightingList()){
//            jdbc.update(insertSuperVillainSighting,
//                    sv.getVillainId(),
//                    s.getSightingId()
//                    );
//        }
//    }


    private Superpowers getPowerForVillain(int id) {
//       final String selectPowerForVillain= "SELECT p.* FROM SuperPower p "
//                + "JOIN SuperVillain s ON s.powerId = p.powerId WHERE s.villainId = ?";
        return jdbc.queryForObject(getPowerForVillain, new PowerMapper(), id);
    }

//private void OrganizationsOfVillain(List<Supervillains> villains){
//    for (Supervillains sv:villains){
//        sv.setOrganizationList(getAllOrgansByVillain(sv.getVillainId()));
//    }
//}
    @Override
    public List<Supervillains> getAllVillains() {
        List<Supervillains> villains = jdbc.query(getAllVillains, new VillainMapper());
        PowerOrgSightings(villains);
        return villains;
    }

    private void PowerOrgSightings(List<Supervillains> villains) {
        for (Supervillains v : villains) {
            v.setOrganizationList(getAllOrgansByVillain(v.getVillainId()));
            v.setSightingList(getAllSighitngsByVillain(v.getVillainId()));
            v.setSuperPower(getPowerForVillain(v.getVillainId()));
        }
    }

    @Override
    public List<Supervillains> getAllVillainsByOrgan(int organizationId) {
        return jdbc.query(getAllVillainsByOrgan, new VillainMapper(), organizationId);
    }

    @Override
    public List<Supervillains> getAllVillainsByLocation(Location location) {
        return jdbc.query(getAllVillainsByLocation, new VillainMapper(), location);
    }

    @Override
    public List<Organizations> getAllOrgansByVillain(int villainID) {
        return jdbc.query(getAllOrgansByVillain, new OrganizationsDaoDB.OrganMapper(), villainID);
    }

    public List<Sightings> getAllSighitngsByVillain(int villainID) {
        return jdbc.query(getAllSighitngsByVillain, new SightingsDaoDB.SightingMapper(), villainID);
    }

    @Override
    @Transactional
    public void updateVillain(Supervillains sv) {
        jdbc.update(updateVillain,
                sv.getName(),
                sv.getDescription(),
                sv.getSuperPower().getPowerId(),
                sv.getVillainId());
        final String deleteOrganizationVillain = "delete from OrganizationVillain where villainId=?";
        jdbc.update(deleteOrganizationVillain, sv.getVillainId());
        for (Organizations o : sv.getOrganizationList()) {
            insertVillainOrganization(o.getOrganizationId(), sv.getVillainId());
        }

    }

    @Override
    @Transactional
    public void deleteVillain(int villainID) {
        final String deleteOrganizationVillain = "delete from OrganizationVillain where villainId=?";
        final String deleteSuperVillainSighting = "delete from SuperVillainSighting where villainId=?";
        jdbc.update(deleteOrganizationVillain, villainID);
        jdbc.update(deleteSuperVillainSighting, villainID);
        jdbc.update(deleteVillain, villainID);
    }

    public static final class VillainMapper implements RowMapper<Supervillains> {

        @Override
        public Supervillains mapRow(ResultSet rs, int i) throws SQLException {
            Supervillains sv = new Supervillains();
            sv.setVillainId(rs.getInt("villainId"));
            sv.setName(rs.getString("name"));
            sv.setDescription(rs.getString("description"));
            return sv;
        }

    }

}
