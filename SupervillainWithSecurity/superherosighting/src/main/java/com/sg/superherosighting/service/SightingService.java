/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.dtos.Sightings;
import com.sg.superherosighting.dtos.Supervillains;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author anna
 */
public interface SightingService {

    //█Sighting Screen█
    Sightings addSighting(Sightings sighting);

    Sightings viewSighting(int sightingId);

    List<Sightings> viewAllSighting();

    void updateSighting(Sightings sighting);

    void deleteSighting(int sightingId);

    List<Sightings> viewAllSightingsByDate(LocalDate date);
    List<Supervillains> viewAllVillainsBySighitng (Sightings sighting);
    List<Sightings> newsfeed (); //for the newsfeed 
}
