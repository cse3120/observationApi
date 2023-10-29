package com.example.observation.service;

import java.util.List;
import java.util.Optional;

import com.example.observation.dto.ObservationDTO;
import com.example.observation.response.ObservationResponse;
import org.springframework.http.ResponseEntity;

public interface ObservationService {

	List<ObservationResponse> getAllObservations();

	ObservationDTO createObservation(ObservationDTO observationDTO);

	Optional<ObservationDTO> getObservationById(long observationId);
	
	ObservationDTO modifyObservation(ObservationDTO observationDTO);

	ResponseEntity<Object> deleteObservation(long observationId);

}
