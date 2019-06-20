/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.dtos.Superpowers;
import java.util.List;

/**
 *
 * @author anna
 */
public interface SuperpowerService {
    //█Superpower screen█

    Superpowers createPower(Superpowers superpower);

    Superpowers getPower(int superPowerId);

    List<Superpowers> getAllPowers();

    void updatePower(Superpowers superpower);

    void deletePower(int superPowerId);
}
