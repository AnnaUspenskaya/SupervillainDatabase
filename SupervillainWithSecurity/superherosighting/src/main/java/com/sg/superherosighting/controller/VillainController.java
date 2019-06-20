/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.daos.SupervillainsDao;
import com.sg.superherosighting.dtos.Organizations;
import com.sg.superherosighting.dtos.Sightings;
import com.sg.superherosighting.dtos.Superpowers;
import com.sg.superherosighting.dtos.Supervillains;
import com.sg.superherosighting.service.OrganizationService;
import com.sg.superherosighting.service.SightingService;
import com.sg.superherosighting.service.SuperpowerService;
import com.sg.superherosighting.service.VillainService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author anna
 */
@Controller
public class VillainController {
    
    private VillainService service;
    private SightingService sightingS;
    private OrganizationService organS;
    private SuperpowerService powerS;
            Set<ConstraintViolation<Supervillains>> violations = new HashSet<>();


    @Autowired

    public VillainController(VillainService service, SightingService sightingS, OrganizationService organS, SuperpowerService powerS) {
        this.service = service;
        this.sightingS=sightingS;
        this.organS=organS;
        this.powerS=powerS;

    }

    @GetMapping("villains")
    public String displayVillains(Model model) {
        List<Supervillains> villains = service.getAllVillains();
        List<Sightings> sightings = sightingS.viewAllSighting();
        List<Organizations>organs = organS.getAllOrgans();
        List<Superpowers> powers = powerS.getAllPowers();
        model.addAttribute("powers", powers);
        model.addAttribute("organs", organs);
        model.addAttribute("sightings", sightings);
        model.addAttribute("villains", villains);
        model.addAttribute("errors", violations);

        return "villains";
    }

  @PostMapping("addVillain")
    public String addVillain(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String power = request.getParameter("powerId");
        String[] organs = request.getParameterValues("organizationId");
        String[] sightings = request.getParameterValues("sightingId");
        Supervillains sv = new Supervillains();
        sv.setName(name);
        sv.setDescription(description);
        sv.setSuperPower(powerS.getPower(Integer.parseInt(power)));
        List<Organizations> organizations = new ArrayList<>();
     if (organs!=null){
        for (String organId : organs) {
            organizations.add(organS.getOrganById(Integer.parseInt(organId)));
        }
               

 
            sv.setOrganizationList(organizations);
     }
        List<Sightings>sightingsL = new ArrayList<>();
//        for (String s: sightings){
//            sightingsL.add(sightingS.viewSighting(Integer.parseInt(s)));
//        }
        //sv.setSightingList(sightingsL);
                Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
                violations = validate.validate(sv);
                
                if (violations.isEmpty()) {
                    service.createVillain(sv);
                }
                return "redirect:/villains";
            }
 
            

    @GetMapping("deleteVillain")
    public String deleteVillain(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("villainId"));
        service.deleteVillain(id);

        return "redirect:/villains";
    }

    @GetMapping("editVillain")
    public String editVillain(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("villainId"));
        Supervillains sv = service.getVillainById(id);
        model.addAttribute("sv", sv);

//         String power = request.getParameter("powerId");
//         sv.setSuperPower(powerS.getPower(Integer.parseInt(power)));
        List<Superpowers> powers = powerS.getAllPowers();
        model.addAttribute("powers", powers);
        List<Organizations>organs = organS.getAllOrgans();
        model.addAttribute("organs", organs);

        return "editVillain";
    }

    @PostMapping("editVillain")
    public String performEditVillain(@Valid Supervillains sv, BindingResult result, HttpServletRequest request) {

        String powerId=request.getParameter("powerId");
        String[] organIds=request.getParameterValues("organizationId");
        sv.setSuperPower(powerS.getPower(Integer.parseInt(powerId)));
                List<Organizations> organizations = new ArrayList<>();
        for (String o : organIds) {
            organizations.add(organS.getOrganById(Integer.parseInt(o)));
        }
            sv.setOrganizationList(organizations);
        if (result.hasErrors() == true) {
            return "editVillain";
        }
        service.updateVillain(sv);
        return "redirect:/villains";
    }
    @GetMapping("villainDetails")
    public String villainDetails(Integer villainId, Model model){
        Supervillains v = service.getVillainById(villainId);
        model.addAttribute("sv",v);
        return "villainDetails";
    }

}
