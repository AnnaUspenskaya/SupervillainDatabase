/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dtos;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author ctrop
 */
public class Supervillains {
    
    private int villainId;
    @NotBlank(message="Name must not be empty")
    @Size(max=45, message="Name must be less than 45 characrters")
    private String name;
    @Size(max=300, message="Desctiption is too long, it must be less than 300 characters")
    private String description;
 
    private Superpowers superPower;
    List<Organizations>organizationList;
    List<Sightings>sightingList;

    public List<Sightings> getSightingList() {
        return sightingList;
    }

    public void setSightingList(List<Sightings> sightingList) {
        this.sightingList = sightingList;
    }
    
    
    public int getVillainId() {
        return villainId;
    }

    public void setVillainId(int villainId) {
        this.villainId = villainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Superpowers getSuperPower() {
        return superPower;
    }

    public void setSuperPower(Superpowers superPower) {
        this.superPower = superPower;
    }

    public List<Organizations> getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(List<Organizations> organizationList) {
        this.organizationList = organizationList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.villainId;
        hash = 83 * hash + Objects.hashCode(this.name);
        hash = 83 * hash + Objects.hashCode(this.description);
        hash = 83 * hash + Objects.hashCode(this.superPower);
        hash = 83 * hash + Objects.hashCode(this.organizationList);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Supervillains other = (Supervillains) obj;
        if (this.villainId != other.villainId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.superPower, other.superPower)) {
            return false;
        }
        if (!Objects.equals(this.organizationList, other.organizationList)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
