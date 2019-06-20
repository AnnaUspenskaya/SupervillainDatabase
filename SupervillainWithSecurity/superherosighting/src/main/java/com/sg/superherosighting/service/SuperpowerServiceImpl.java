/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.daos.SuperpowersDao;
import com.sg.superherosighting.dtos.Superpowers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author anna
 */
@Service
public class SuperpowerServiceImpl implements SuperpowerService{
@Autowired
private SuperpowersDao powerDao;

@Autowired
    public SuperpowerServiceImpl(SuperpowersDao powerDao) {
        this.powerDao = powerDao;
    }

    @Override
    @Transactional
    public Superpowers createPower(Superpowers sp) {
sp=this.powerDao.createPower(sp);
//maybe for loop for villains
return sp;
    }

    @Override
    public Superpowers getPower(int superPowerId) {
return powerDao.getPower(superPowerId);
    }

    @Override
    public List<Superpowers> getAllPowers() {
return powerDao.getAllPowers();
    }

    @Override
    public void updatePower(Superpowers superpower) {
 powerDao.updatePower(superpower); 
    }

    @Override
    public void deletePower(int superPowerId) {
powerDao.deletePower(superPowerId);
    }
    
}
