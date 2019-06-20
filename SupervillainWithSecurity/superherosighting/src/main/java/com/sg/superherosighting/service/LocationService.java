/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.dtos.Location;
import com.sg.superherosighting.dtos.Supervillains;
import java.util.List;

/**
 *
 * @author anna
 */
public interface LocationService {
        //█Location Screen█
    Location createLocation(Location location);

    Location getLocationById(int locationId);

    List<Location> getAllLocations();

    void updateLocation(Location location);

    void deleteLocation(int locationId);

//    List<Location>getLocationsforVillain(Supervillains supervillain);
}
