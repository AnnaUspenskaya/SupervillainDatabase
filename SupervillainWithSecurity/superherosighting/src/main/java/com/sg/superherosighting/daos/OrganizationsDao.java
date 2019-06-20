/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.daos;

import com.sg.superherosighting.dtos.Organizations;
import com.sg.superherosighting.dtos.Supervillains;
import java.util.List;

/**
 *
 * @author ctrop
 */
public interface OrganizationsDao {
    
    //crud methods
    
    public Organizations createOrgan(Organizations organization);
    public Organizations getOrganById(int organizationId);
    public List<Organizations> getAllOrgans();
    public List<Organizations> listAllOrgansOfVillain(int VillainID);
    public List<Supervillains>listAllVillainsOfOrgan(int organizationId);
    public void updateOrgan(Organizations organization);
    public void deleteOrang(int organizationId);
    
    
    
    
}
