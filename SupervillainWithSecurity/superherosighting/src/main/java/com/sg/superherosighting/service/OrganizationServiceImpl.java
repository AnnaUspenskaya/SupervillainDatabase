/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.daos.OrganizationsDao;
import com.sg.superherosighting.daos.SupervillainsDao;
import com.sg.superherosighting.dtos.Organizations;
import com.sg.superherosighting.dtos.Supervillains;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author anna
 */
@Service
public class OrganizationServiceImpl implements OrganizationService{
    @Autowired
private OrganizationsDao OrgDao;
    private SupervillainsDao villainDao;
@Autowired
    public OrganizationServiceImpl(OrganizationsDao OrgDao, SupervillainsDao villainDao) {
        this.OrgDao = OrgDao;
        this.villainDao=villainDao;
    }
    
    @Override
    public Organizations createOrgan(Organizations o) {
o=this.OrgDao.createOrgan(o);
return o;
    }

    @Override
    public Organizations getOrganById(int organizationId) {
return OrgDao.getOrganById(organizationId);
//villains!!!
    }

    @Override
    public List<Organizations> getAllOrgans() {
return OrgDao.getAllOrgans();
    }

    @Override
    public void updateOrgan(Organizations organization) {
OrgDao.updateOrgan(organization);  
    }

    @Override
    public void deleteOrang(int organizationId) {
OrgDao.deleteOrang(organizationId);    
    }

    
    @Override
    public List<Organizations> getAlllOrgansByVillain(int villainID) {
return OrgDao.listAllOrgansOfVillain(villainID);
    }
    
    
}
