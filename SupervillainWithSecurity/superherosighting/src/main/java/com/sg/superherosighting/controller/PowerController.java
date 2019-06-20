/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.dtos.Superpowers;
import com.sg.superherosighting.dtos.Supervillains;
import com.sg.superherosighting.service.SuperpowerService;
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
public class PowerController {
    private SuperpowerService service;
        Set<ConstraintViolation<Superpowers>> violations = new HashSet<>();
@Autowired
    public PowerController(SuperpowerService service) {
        this.service = service;
    }
 @GetMapping("power")
    public String displayPowers(Model model) {
        List<Superpowers> powers = service.getAllPowers();
        model.addAttribute("powers", powers);
        model.addAttribute("errors", violations);

        return "power";
    }

    @PostMapping("addPower")
    public String addPower(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
Superpowers p = new Superpowers();
        p.setName(name);
        p.setDescription(description);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(p);

        if (violations.isEmpty()) {
            service.createPower(p);
        }
        return "redirect:/power";
    }

    @GetMapping("deletePower")
    public String deletePower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("powerId"));
        service.deletePower(id);

        return "redirect:/power";
    }

    @GetMapping("editPower")
    public String editPower(HttpServletRequest request, Model model) {
        
        int id = Integer.parseInt(request.getParameter("powerId"));
        Superpowers p = service.getPower(id);
        model.addAttribute("p", p);
        return "editPower";
    }

    @PostMapping("editPower")
    public String performEditPower(@Valid Superpowers p, BindingResult result) {
        //String 
        if (result.hasErrors() == true) {
            return "editPower";
        }
        service.updatePower(p);
        return "redirect:/power";
    }
}
