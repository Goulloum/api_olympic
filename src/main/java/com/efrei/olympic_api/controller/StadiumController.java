package com.efrei.olympic_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efrei.olympic_api.dto.CreateStadiumDto;
import com.efrei.olympic_api.model.Stadium;
import com.efrei.olympic_api.service.StadiumService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/stadiums")
public class StadiumController {

    @Autowired
    private StadiumService stadiumService;

    @GetMapping
    public ResponseEntity<List<Stadium>> findAllStadiums() {

        List<Stadium> stadiums = stadiumService.allStadiums();

        return new ResponseEntity<>(stadiums, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stadium> findById(@PathVariable Integer id) {

        Stadium stadium = stadiumService.getStadiumById(id);

        return new ResponseEntity<>(stadium, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Stadium> createStadium(@Valid CreateStadiumDto stadium) {

        Stadium newStadium = stadiumService.createStadium(stadium);

        return new ResponseEntity<>(newStadium, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStadium(@PathVariable Integer id) {

        stadiumService.deleteStadium(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stadium> updateStadium(@PathVariable Integer id, @Valid CreateStadiumDto stadium) {

        Stadium updatedStadium = stadiumService.updateStadium(id, stadium);

        return new ResponseEntity<>(updatedStadium, HttpStatus.OK);
    }

}
