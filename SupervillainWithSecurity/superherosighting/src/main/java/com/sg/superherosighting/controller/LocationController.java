/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.dtos.Location;
import com.sg.superherosighting.service.LocationService;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author anna
 */
@Controller
public class LocationController {

    private LocationService service;
    Set<ConstraintViolation<Location>> violations = new HashSet<>();

    @Autowired
    public LocationController(LocationService service) {
        this.service = service;
    }

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locationList = service.getAllLocations();
        model.addAttribute("locationList", locationList);
        model.addAttribute("errors", violations);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zipCode = request.getParameter("zipCode");
        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");

        Location l = new Location();
        l.setName(name);
        l.setDescription(description);
        l.setStreet(street);
        l.setCity(city);
        l.setState(state);
        l.setZipCode(zipCode);
        l.setLongitude(longitude);
        l.setLatitude(latitude);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(l);
        if (violations.isEmpty()) {
            service.createLocation(l);
        }
        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("locationId"));
        service.deleteLocation(id);
        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        model.addAttribute("errors", violations);
        int id = Integer.parseInt(request.getParameter("locationId"));
        Location l = service.getLocationById(id);
        model.addAttribute("location", l);

        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location l, BindingResult result, Model model, HttpServletRequest request) {
                model.addAttribute("errors", violations);

//        String city = request.getParameter("city");
//        if(city==null){
//            FieldError error = new FieldError("course", "students", "Must include one student");
//            result.addError(error);
//        }
        if (result.hasErrors()) {
            return "editLocation";
        }
        service.updateLocation(l);
        return "redirect:/locations";
    }

}
