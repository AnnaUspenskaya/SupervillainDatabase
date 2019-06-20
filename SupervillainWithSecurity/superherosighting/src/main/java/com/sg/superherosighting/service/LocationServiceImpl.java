/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.daos.LocationDao;
import com.sg.superherosighting.dtos.Location;
import com.sg.superherosighting.dtos.Supervillains;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author anna
 */
@Service
public class LocationServiceImpl implements LocationService{
    @Autowired
private LocationDao locationDao;

@Autowired 
    public LocationServiceImpl(LocationDao locationDao) {
        this.locationDao=locationDao;
    }

    @Override
    public Location createLocation(Location location) {
return locationDao.createLocation(location);
    }

    @Override
    public Location getLocationById(int locationId) {
return locationDao.getLocationById(locationId);
    }

    @Override
    public List<Location> getAllLocations() {
return locationDao.getAllLocations();
    }

    @Override
    public void updateLocation(Location location) {
locationDao.updateLocation(location);    
    }

    @Override
    public void deleteLocation(int locationId) {
locationDao.deleteLocation(locationId);    
    }

//    @Override
//    public List<Location> getLocationsforVillain(Supervillains supervillain) {
//return locationDao..getLocationsforVillain(supervillain);
//    }
    
}
