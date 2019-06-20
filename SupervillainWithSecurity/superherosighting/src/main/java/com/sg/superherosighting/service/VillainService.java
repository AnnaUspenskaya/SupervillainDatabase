/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.dtos.Location;
import com.sg.superherosighting.dtos.Organizations;
import com.sg.superherosighting.dtos.Sightings;
import com.sg.superherosighting.dtos.Superpowers;
import com.sg.superherosighting.dtos.Supervillains;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author anna
 */
public interface VillainService {

    //█Supervillain screen█
    Supervillains createVillain(Supervillains villain);

    Supervillains getVillainById(int villainID);

    List<Supervillains> getAllVillains();

    void updateVillain(Supervillains villain);

    void deleteVillain(int villainID);

    List<Supervillains> getAllVillainsByLocation(Location location);

    List<Supervillains> getAllVillainsByOrgan(int organizationId);

}
