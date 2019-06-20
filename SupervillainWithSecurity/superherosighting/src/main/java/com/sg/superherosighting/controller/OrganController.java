/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.dtos.Location;
import com.sg.superherosighting.dtos.Organizations;
import com.sg.superherosighting.dtos.Supervillains;
import com.sg.superherosighting.service.LocationService;
import com.sg.superherosighting.service.OrganizationService;
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
public class OrganController {

    private OrganizationService service;
    private VillainService villainS;
    private LocationService locationS;
    Set<ConstraintViolation<Organizations>> violations = new HashSet<>();

    @Autowired

    public OrganController(OrganizationService service, VillainService villainS, LocationService locationS) {
        this.service = service;
        this.villainS=villainS;
        this.locationS=locationS;
    }

    @GetMapping("organizations")
    public String displayOrganization(Model model) {
        List<Organizations> organs = service.getAllOrgans();
        List<Supervillains> villains = villainS.getAllVillains();
        List<Location> locationList = locationS.getAllLocations();
        model.addAttribute("locationList", locationList);
        model.addAttribute("villains", villains);
        model.addAttribute("organs", organs);
        model.addAttribute("errors", violations);

        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String phone = request.getParameter("phoneNum");
        String locationId = request.getParameter("locationId");
        //String[] villains =request.getParameterValues("villainId");
        Organizations organ = new Organizations();
        organ.setName(name);
        organ.setDescription(description);
        organ.setPhoneNum(phone);
        organ.setLocation(locationS.getLocationById(Integer.parseInt(locationId)));
//        List<Supervillains> supervillains = new ArrayList<>();
//        for (String villainId: villains){
//            supervillains.add(villainS.getVillainById(Integer.parseInt(villainId)));
//        }
//        o.setMembers(supervillains);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organ);

        if (violations.isEmpty()) {
            service.createOrgan(organ);
        }
        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("organId"));
        service.deleteOrang(id);

        return "redirect:/organizations";
    }

//    @GetMapping("editOrganization")
//    public String editOrganization(HttpServletRequest request, Model model) {
//        int id = Integer.parseInt(request.getParameter("organId"));
//        Organizations organ = service.getOrganById(id);
//        List<Location> locationList = locationS.getAllLocations();
//        model.addAttribute("locationList",locationList);
//        model.addAttribute("organ", organ);
//        return "editOrganization";
//    }
    
        @GetMapping("editOrganization")
    public String editOrganization(Integer organId, Model model) {
        Organizations organ = service.getOrganById(organId);
        List<Location> locations = locationS.getAllLocations();
        //mode.addAttribute(or)
        model.addAttribute("locations",locations);
        model.addAttribute("organ", organ);
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganizations(@Valid Organizations organ, BindingResult result, HttpServletRequest request, Model model) {
        String locationId = request.getParameter("locationId");
        organ.setLocation(locationS.getLocationById(Integer.parseInt(locationId)));
                    //model.addAttribute("locations", locationS.getAllLocations() );

        if (result.hasErrors() == true) {
            return "editOrganization";
        }
        service.updateOrgan(organ);
        return "redirect:/organizations";
    }
    
    @GetMapping("organizationDetails")
    public String organDetails(Integer organId, Model model){
        List<Supervillains> villains = villainS.getAllVillainsByOrgan(organId);
        model.addAttribute("villains", villains);
        Organizations organ = service.getOrganById(organId);
        model.addAttribute("organ",organ);
        return "organizationDetails";
    }
    

}
