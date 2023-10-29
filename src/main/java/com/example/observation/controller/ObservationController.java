package com.example.observation.controller;

import com.example.observation.dto.ObservationDTO;
import com.example.observation.exception.ObservationException;
import com.example.observation.mapper.ObservationMapper;
import com.example.observation.response.ObservationResponse;
import com.example.observation.service.ObservationService;
import com.example.observation.service.impl.ObservationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/observations")
@Validated
public class ObservationController {

    private static final Logger logger = LoggerFactory.getLogger(ObservationServiceImpl.class);
    private final ObservationService observationService;

    public ObservationController(ObservationService observationService) {
        this.observationService = observationService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllObservations() {
        logger.info("Retrieve all observation models");
        List<ObservationResponse> observationResponseList = observationService.getAllObservations();
        if (observationResponseList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No observation model found");
        } else {
            return new ResponseEntity<>(observationService.getAllObservations(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<ObservationDTO> createObservation(@Valid @RequestBody ObservationDTO observationDTO) {
        logger.info("Create new observation model");
        return new ResponseEntity<>(observationService.createObservation(observationDTO), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ObservationDTO> modifyObservation(@Valid @PathVariable("id") int observationId, @Valid @RequestBody ObservationDTO observationDTO) {
        logger.info("Modify observation model for " + observationId);
        return observationService.getObservationById(observationId)
                .map(savedObservation -> {
                    savedObservation.setPatientDTO(observationDTO.getPatientDTO());
                    savedObservation.setObservationValue(observationDTO.getObservationValue());
                    savedObservation.setObservationEnum(observationDTO.getObservationEnum());
                    ObservationDTO modifiedObservation = observationService.modifyObservation(savedObservation);
                    return new ResponseEntity<>(modifiedObservation, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteObservation(@Valid @PathVariable("id") int observationId) {
        logger.info("Delete observation model for " + observationId);
        return observationService.deleteObservation(observationId);
    }
}