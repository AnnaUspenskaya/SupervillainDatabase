/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import com.sg.superherosighting.dtos.Location;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author ctrop
 */
public class Sightings {
    
    private int sightingId;
    private LocalDate date;
    private Location location;
    private List<Supervillains> superVillain;

    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Supervillains> getSuperVillain() {
        return superVillain;
    }

    public void setSuperVillain(List<Supervillains> superVillain) {
        this.superVillain = superVillain;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.sightingId;
        hash = 17 * hash + Objects.hashCode(this.date);
        hash = 17 * hash + Objects.hashCode(this.location);
        hash = 17 * hash + Objects.hashCode(this.superVillain);
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
        final Sightings other = (Sightings) obj;
        if (this.sightingId != other.sightingId) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.superVillain, other.superVillain)) {
            return false;
        }
        return true;
    }
}
