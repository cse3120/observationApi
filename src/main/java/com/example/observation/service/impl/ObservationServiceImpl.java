package com.example.observation.service.impl;


import com.example.observation.dto.ObservationDTO;
import com.example.observation.entity.Observation;
import com.example.observation.entity.Patient;
import com.example.observation.exception.ObservationException;
import com.example.observation.exception.PostgreSQLException;
import com.example.observation.mapper.ObservationMapper;
import com.example.observation.repository.ObservationRepository;
import com.example.observation.repository.PatientRepository;
import com.example.observation.response.ObservationResponse;
import com.example.observation.service.ObservationService;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ObservationServiceImpl implements ObservationService {

    private static final Logger logger = LoggerFactory.getLogger(ObservationServiceImpl.class);

    private final ObservationRepository observationRepository;
    private final PatientRepository patientRepository;

    public ObservationServiceImpl(ObservationRepository observationRepository, PatientRepository patientRepository) {
        this.observationRepository = observationRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public List<ObservationResponse> getAllObservations() {
        List<Observation> observationList = observationRepository.findAll();
        List<ObservationDTO> observationDTOList = new ArrayList<>();
        for (Observation observation : observationList) {
            ObservationDTO observationDTO = ObservationMapper.mapToObservationDTO(observation);
            observationDTOList.add(observationDTO);
        }
        List<ObservationResponse> observationResponseList = observationDTOList.stream().map(observationDTO -> {
            ObservationResponse observationResponse = new ObservationResponse();
            observationResponse.setObservationId(observationDTO.getObservationId());
            observationResponse.setObservationType(observationDTO.getObservationEnum().getObservationTypeName());
            observationResponse.setObservationDateTime(observationDTO.getObservationDateTime());
            observationResponse.setPatientId(observationDTO.getPatientDTO().getPatientId());
            observationResponse.setObservationValue(observationDTO.getObservationValue());
            observationResponse.setObservationUnit(observationDTO.getObservationEnum().getObservationTypeUnit());
            return observationResponse;
        }).collect(Collectors.toList());
        logger.info("Retrieve the Observation Model with a specified size: " + observationDTOList.size());
        return observationResponseList;
    }


    @Override
    public ObservationDTO createObservation(ObservationDTO observationDTO) {
        try {
            validateFields(observationDTO);
            Observation observation = ObservationMapper.mapToObservation(observationDTO);
            Patient patient = savePatient(observation);
            observation.setPatient(patient);
            if (observation.getObservationId() == 0L || !observationRepository.existsById(observation.getObservationId())) {
                Observation savedObservation = observationRepository.save(observation);
                logger.info("New Observation model is created for " + savedObservation.getObservationId());
                return ObservationMapper.mapToObservationDTO(savedObservation);
            } else {
                throw new ObservationException("Observation Id is duplicate");
            }
        } catch (DataIntegrityViolationException ex) {
            logger.error("Unable to create the observation model");
            if (ex.getCause() instanceof PSQLException) {
                throw new PostgreSQLException("A PostgreSQL error occurred: ");
            } else {
                throw new PostgreSQLException("A PostgreSQL-related error occurred: ");
            }
        } catch (DataAccessException ex) {
            throw new PostgreSQLException("A PostgreSQL-related error occurred: ");
        }
    }

    /**
     * Validate ObservationDTO fields.
     *
     * @param observationDTO ObservationDTO
     */
    private void validateFields(ObservationDTO observationDTO) {
        if (observationDTO.getObservationValue() == null) {
            logger.info("ObservationValue field is not available");
            throw new ObservationException("ObservationValue field is not available");
        }
        if (observationDTO.getObservationEnum() == null) {
            logger.info("observationType is not available");
            throw new ObservationException("observationType is not available");
        }
        if (observationDTO.getPatientDTO() == null) {
            logger.info("Patient data is not available");
            throw new ObservationException(" Patient data is not available");
        } else if (observationDTO.getPatientDTO().getPatientId() == null) {
            logger.info("Patient id is not available");
            throw new ObservationException("Patient id is not available");
        }
    }

    /**
     * Save Patient.
     *
     * @param observation Observation
     * @return Patient
     */
    private Patient savePatient(Observation observation) {
        Patient patient = observation.getPatient();
        if (patient != null) {
            patient = patientRepository.save(patient);
        }
        logger.info("Patient is createdOrUpdated");
        return patient;
    }


    @Override
    public ObservationDTO modifyObservation(ObservationDTO observationDTO) {
        try {
            validateFields(observationDTO);
            Observation observation = ObservationMapper.mapToObservation(observationDTO);
            Patient patient = savePatient(observation);
            observation.setPatient(patient);
            Observation updatedObservation;
            updatedObservation = observationRepository.save(observation);
            logger.info("Observation model is createdOrUpdated for: " + observationDTO.getObservationId());
            return ObservationMapper.mapToObservationDTO(updatedObservation);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Unable to modify the observation model");
            if (ex.getCause() instanceof PSQLException) {
                throw new PostgreSQLException("A PostgreSQL error occurred: ");
            } else {
                throw new PostgreSQLException("A PostgreSQL-related error occurred: ");
            }
        } catch (DataAccessException ex) {
            throw new PostgreSQLException("A PostgreSQL-related error occurred: ");
        }
    }


    @Override
    public Optional<ObservationDTO> getObservationById(long observationId) {
        Optional<Observation> optionalObservation = Optional.ofNullable(observationRepository.findById(observationId)
                .orElseThrow(() -> new ObservationException("Observation Id " + observationId + " is not found")));
        return optionalObservation.map(ObservationMapper::mapToObservationDTO);
    }


    @Override
    public ResponseEntity<Object> deleteObservation(long observationId) {
        Optional<Observation> optionalObservation = Optional.ofNullable(observationRepository.findById(observationId)
                .orElseThrow(() -> new ObservationException("Observation Id " + observationId + " is not found")));
        if (optionalObservation.isPresent()) {
            observationRepository.delete(optionalObservation.get());
            logger.info("Observation model is deleted for " + observationId);
            return ResponseEntity.ok().body("Observation model is deleted for " + observationId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Observation deletion is failed - Observation ID: " + observationId);
        }
    }
}
