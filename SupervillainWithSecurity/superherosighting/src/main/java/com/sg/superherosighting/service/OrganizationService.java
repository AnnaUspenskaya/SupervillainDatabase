/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.dtos.Organizations;
import java.util.List;

/**
 *
 * @author anna
 */
public interface OrganizationService {
    //█Organization Screen█

    Organizations createOrgan(Organizations organization);

    Organizations getOrganById(int organizationId);

    List<Organizations> getAllOrgans();

    void updateOrgan(Organizations organization);

    void deleteOrang(int organizationId);

    List<Organizations> getAlllOrgansByVillain(int villainID);

}
