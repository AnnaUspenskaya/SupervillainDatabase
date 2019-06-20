/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.daos;

import com.sg.superherosighting.dtos.Location;
import java.util.List;

/**
 *
 * @author ctrop
 */
public interface LocationDao {
    
    
    //crud methods
    
    public Location createLocation(Location location);
    public Location getLocationById(int locationId);
    public List<Location> getAllLocations();
    public void updateLocation(Location location);
    public void deleteLocation (int locationId);
}
