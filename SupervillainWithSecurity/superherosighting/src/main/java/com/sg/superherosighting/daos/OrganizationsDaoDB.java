/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.daos;

import com.sg.superherosighting.daos.LocationDaoDB.LocationMapper;
import com.sg.superherosighting.daos.SupervillainsDaoDB.VillainMapper;
import com.sg.superherosighting.dtos.Location;
import com.sg.superherosighting.dtos.Organizations;
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
public class OrganizationsDaoDB implements OrganizationsDao{
    @Autowired
    private JdbcTemplate jdbc;
    /*works*/private final String createOrgan = "INSERT INTO Organization (name, description, phone, locationId) VALUES(?,?,?,?)";
    /*works*/private final String getOrganById = "SELECT * FROM Organization where organizationId =?";
    /*works*/private final String getAllOrgans = "SELECT * FROM Organization";
    /*works*/private final String updateOrgan = "UPDATE Organization SET name=?, description =?, phone=?, locationId=? WHERE organizationId=?";
    /*works*/private final String deleteOrang = "DELETE FROM Organization WHERE organizationId = ?";
//many to many 
    private final String listAllOrgansOfVillain = "SELECT * FROM Organization INNER JOIN OrganizationVillain on "
            + "Organization.organizationId=OrganizationVillain.organizationId "
            + "where villainId=?";
        private final String listAllVillainsOfOrgan = "SELECT * FROM SuperVillain INNER JOIN OrganizationVillain on "
            + "SuperVillain.villainId=OrganizationVillain.villainId "
            + "where organizationId=?";
        private final String locationForOrganization ="SELECT l.* FROM Location l JOIN Organization o ON o.locationId = l.locationId WHERE o.organizationId = ?";
    @Override
    @Transactional
    public Organizations createOrgan(Organizations o) {
jdbc.update(createOrgan,
        o.getName(),
        o.getDescription(),
        o.getPhoneNum(),
o.getLocation().getLocationId());
int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
o.setOrganizationId(newId);
//insertOrganizationVillain(o);
return o;
    }
    private void insertOrganizationVillain(Organizations o){
        final String insertOrganizationVillain = "INSERT INTO OrganizationVillain (organizationId, villainId) values (?,?)";
        for(Supervillains v:o.getMembers()){
            jdbc.update(insertOrganizationVillain,
            o.getOrganizationId(),
            v.getVillainId());
        }
    }
    @Override
    public Organizations getOrganById(int organizationId) {
try{
    Organizations organ = jdbc.queryForObject(getOrganById, new OrganMapper(), organizationId);
    organ.setMembers(listAllVillainsOfOrgan(organizationId));
    organ.setLocation(locationForOrganization(organizationId));
    return organ;
}catch(DataAccessException ex){
    return null;
}    
    }
    
private Location locationForOrganization(int  organId){
    return jdbc.queryForObject(locationForOrganization, new LocationMapper(), organId);
}
    @Override
    public List<Organizations> getAllOrgans() {
return jdbc.query(getAllOrgans, new OrganMapper());
    }

    @Override
    public List<Organizations> listAllOrgansOfVillain(int VillainID) {
return jdbc.query(listAllOrgansOfVillain, new OrganMapper(), VillainID);
    }
    

    @Override
    @Transactional
    public void updateOrgan(Organizations o) {
int changes = jdbc.update(updateOrgan,
                o.getName(),
        o.getDescription(),
        o.getPhoneNum(),
        o.getLocation().getLocationId(),
        o.getOrganizationId()); 
        System.out.println(changes);
 //final String deleteOrganizationVillain ="delete from OrganizationVillain where organizationId=?";
    //jdbc.update(deleteOrganizationVillain, o.getOrganizationId());
 //insertOrganizationVillain(o);
    }

    @Override
    @Transactional
    public void deleteOrang(int organizationId) {
final String deleteOrganizationVillain ="delete From OrganizationVillain where organizationId =?";
jdbc.update(deleteOrganizationVillain, organizationId);
jdbc.update(deleteOrang, organizationId);

   }

    @Override
    public List<Supervillains> listAllVillainsOfOrgan(int organizationId) {
return jdbc.query(listAllVillainsOfOrgan, new VillainMapper(), organizationId);
    }
    public static final class OrganMapper implements RowMapper<Organizations>{

        @Override
        public Organizations mapRow(ResultSet rs, int i) throws SQLException {
Organizations o = new Organizations();
o.setOrganizationId(rs.getInt("organizationId"));
o.setName(rs.getString("name")); 
o.setDescription(rs.getString("description"));
o.setPhoneNum(rs.getString("phone"));

       return o; }
        
    }
}
