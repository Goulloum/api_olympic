package com.efrei.olympic_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efrei.olympic_api.dto.CreateStadiumDto;
import com.efrei.olympic_api.enums.EntityEnum;
import com.efrei.olympic_api.exception.RessourceNotFoundException;
import com.efrei.olympic_api.model.Stadium;
import com.efrei.olympic_api.repository.StadiumRepository;

import jakarta.validation.Valid;

@Service
public class StadiumService {

    @Autowired
    private StadiumRepository stadiumRepository;

    public List<Stadium> allStadiums() {
        ArrayList<Stadium> stadiums = new ArrayList<Stadium>();

        stadiumRepository.findAll().forEach(stadiums::add);

        return stadiums;
    }

    public Stadium getStadiumById(Integer id) {
        Optional<Stadium> stadium = stadiumRepository.findById(id);

        if (stadium.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.STADIUM);
        }

        return stadium.get();
    }

    public Stadium createStadium(CreateStadiumDto stadium) {
        Stadium newStadium = new Stadium();

        newStadium.setName(stadium.getName());
        newStadium.setCapacity(stadium.getCapacity());
        newStadium.setCity(stadium.getCity());
        newStadium.setPostalCode(stadium.getPostalCode());
        newStadium.setStreetAddress(stadium.getStreetAddress());

        return stadiumRepository.save(newStadium);

    }

    public void deleteStadium(Integer id) {
        Optional<Stadium> stadium = stadiumRepository.findById(id);

        if (stadium.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.STADIUM);
        }

        stadiumRepository.deleteById(id);
    }

    public Stadium updateStadium(Integer id, @Valid CreateStadiumDto stadium) {
        Optional<Stadium> stadiumToUpdate = stadiumRepository.findById(id);

        if (stadiumToUpdate.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.STADIUM);
        }

        Stadium updatedStadium = stadiumToUpdate.get();

        updatedStadium.setName(stadium.getName());
        updatedStadium.setCapacity(stadium.getCapacity());
        updatedStadium.setCity(stadium.getCity());
        updatedStadium.setPostalCode(stadium.getPostalCode());
        updatedStadium.setStreetAddress(stadium.getStreetAddress());

        return stadiumRepository.save(updatedStadium);
    }

}
