/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.daos.SightingsDao;
import com.sg.superherosighting.daos.SupervillainsDao;
import com.sg.superherosighting.dtos.Sightings;
import com.sg.superherosighting.dtos.Supervillains;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author anna
 */
@Service
public class SightingServiceImpl implements SightingService {

    @Autowired
    private SightingsDao sightingDao;
    private SupervillainsDao villainDao;

    @Autowired
    public SightingServiceImpl(SightingsDao sightingDao, SupervillainsDao villainDao) {
        this.sightingDao = sightingDao;
        this.villainDao = villainDao;
    }

    @Override
    public Sightings addSighting(Sightings s) {
        this.sightingDao.addSighting(s);
//        if (s.getSuperVillain().size() > 0) {
//            for (Supervillains sv : s.getSuperVillain()) {
//                if (sv.getVillainId() == 0) {
//                    //sv = this.villainDao.createVillain(sv);
//                   // this.sightingDao.
//                }
//
//            }
//        }
        return s;
    }

    @Override
    public Sightings viewSighting(int sightingId) {
        return sightingDao.viewSighting(sightingId);
    }

    @Override
    public List<Sightings> viewAllSighting() {
        return sightingDao.viewAllSighting();
    }

    @Override
    public void updateSighting(Sightings sighting) {
        sightingDao.updateSighting(sighting);
    }

    @Override
    public void deleteSighting(int sightingId) {
        sightingDao.deleteSighting(sightingId);
    }

    @Override
    public List<Sightings> viewAllSightingsByDate(LocalDate date) {
        return sightingDao.viewAllSightingsByDate(date);
    }

    @Override
    public List<Sightings> newsfeed() {
        return sightingDao.Newsfeed();
    }

    @Override
    public List<Supervillains> viewAllVillainsBySighitng(Sightings sighting) {
        return sightingDao.viewAllVillainsBySighitng(sighting);
    }

}
