/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.dtos.Location;
import com.sg.superherosighting.dtos.Sightings;
import com.sg.superherosighting.dtos.Supervillains;
import com.sg.superherosighting.service.LocationService;
import com.sg.superherosighting.service.SightingService;
import com.sg.superherosighting.service.VillainService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class SightingController {
    private SightingService service;
    private LocationService locationS;
    private VillainService villainS;
    
        Set<ConstraintViolation<Sightings>> violations = new HashSet<>();
@Autowired

    public SightingController(SightingService service, LocationService locationS, VillainService villainS) {
        this.service = service;
        this.locationS=locationS;
        this.villainS=villainS;
    }
    @GetMapping("home")
    public String newsgeed(Model model){
        List<Sightings> newsfeed = service.newsfeed();
        model.addAttribute("newsfeed", newsfeed);
        return "home";
    }
    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sightings> sightings = service.viewAllSighting();
                List<Location> locationList = locationS.getAllLocations();
                List<Supervillains> villains = villainS.getAllVillains();
                model.addAttribute("villains", villains);
        model.addAttribute("locationList", locationList);
        model.addAttribute("sightings", sightings);
        model.addAttribute("errors", violations);

        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        String date = request.getParameter("date");
        String location = request.getParameter("locationId");
        String[] villainIds = request.getParameterValues("villainId");
        Sightings s = new Sightings();
        s.setDate(LocalDate.parse(date));
       s.setLocation(locationS.getLocationById(Integer.parseInt(location)));
       List<Supervillains> villains = new ArrayList<>();
       for (String v: villainIds){
           villains.add(villainS.getVillainById(Integer.parseInt(v)));
       }
       s.setSuperVillain(villains);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(s);

        if (violations.isEmpty()) {
            service.addSighting(s);
        }
        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("sightingId"));
        service.deleteSighting(id);

        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("sightingId"));
        Sightings s = service.viewSighting(id);
                List<Location> locationList = locationS.getAllLocations();
                List<Supervillains> villains = villainS.getAllVillains();
                model.addAttribute("villains", villains);
        model.addAttribute("locations", locationList);
        model.addAttribute("sighting", s);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighitng(@Valid Sightings s, BindingResult result, HttpServletRequest request, Model model) {
        String date = request.getParameter("date");
        String location = request.getParameter("locationId");
        String[] villainIds = request.getParameterValues("villainId");
        s.setDate(LocalDate.parse(date));
       s.setLocation(locationS.getLocationById(Integer.parseInt(location)));
       List<Supervillains> villains = new ArrayList<>();
       for (String v: villainIds){
           villains.add(villainS.getVillainById(Integer.parseInt(v)));
       }
       s.setSuperVillain(villains);

        if (result.hasErrors() == true) {
            return "editSighting";
        }
        service.updateSighting(s);
        return "redirect:/sightings";
    }
    
    @GetMapping("sightingDetails")
public String sightingDetails (Integer sightingId, Model model){
    Sightings s = service.viewSighting(sightingId);
//    Location location = s.getLocation();
//    model.addAttribute("location",location);
    model.addAttribute("sighting", s);
    return "sightingDetails";   
}

//        
//        List<Supervillains> villains = villainS.getAllVillainsByOrgan(organId);
//        model.addAttribute("villains", villains);
//        Sightings  s = service..getOrganById(organId);
//        model.addAttribute("o",o);
//        return "sightingDetails";
    }

    

