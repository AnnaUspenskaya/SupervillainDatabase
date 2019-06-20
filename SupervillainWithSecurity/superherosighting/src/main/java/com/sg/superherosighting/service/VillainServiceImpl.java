/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.daos.LocationDao;
import com.sg.superherosighting.daos.OrganizationsDao;
import com.sg.superherosighting.daos.SightingsDao;
import com.sg.superherosighting.daos.SupervillainsDao;
import com.sg.superherosighting.dtos.Location;
import com.sg.superherosighting.dtos.Organizations;
import com.sg.superherosighting.dtos.Sightings;
import com.sg.superherosighting.dtos.Supervillains;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author anna
 */
@Service
public class VillainServiceImpl implements VillainService{
 @Autowired    
private SupervillainsDao villainDao;
private LocationDao locationDao;
private SightingsDao sightingDao;
private OrganizationsDao organDao;

@Autowired 
    public VillainServiceImpl(SupervillainsDao villainDao, OrganizationsDao organDao, SightingsDao sightingDao) {
        this.villainDao = villainDao;
        this.organDao=organDao;
        this.sightingDao= sightingDao;
        
    }
        
    @Override
    @Transactional 
    public Supervillains createVillain(Supervillains sv) {
sv = this.villainDao.createVillain(sv);
//if(sv.getOrganizationList().size()>0){
//    for (Organizations o:sv.getOrganizationList()){
//        if(o.getOrganizationId()==0){
//            o=this.organDao.createOrgan(o);
//        }
//
//        this.villainDao.getAllOrgansByVillain(sv.getVillainId());
//       // this.villainDao.getAllVillainsByLocation(sv.getVillainId());
//    }
//}
   return sv;
    }



    @Override
    public Supervillains getVillainById(int villainID) {
return villainDao.getVillainById(villainID);
    }

    @Override
    public List<Supervillains> getAllVillains() {
return villainDao.getAllVillains();
    }

    @Override
    public void updateVillain(Supervillains villain) {
villainDao.updateVillain(villain);
    }

    @Override
    @Transactional
    public void deleteVillain(int villainID) {
villainDao.deleteVillain(villainID);    
    }

    @Override
    public List<Supervillains> getAllVillainsByLocation(Location location) {
return villainDao.getAllVillainsByLocation(location);
    }

    @Override
    public List<Supervillains> getAllVillainsByOrgan(int organizationId) {
return villainDao.getAllVillainsByOrgan(organizationId);
    }
    
}
