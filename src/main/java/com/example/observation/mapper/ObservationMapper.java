package com.example.observation.mapper;

import com.example.observation.dto.ObservationDTO;
import com.example.observation.dto.ObservationEnum;
import com.example.observation.entity.Observation;
import com.example.observation.response.ErrorInfoResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ObservationMapper {

    /**
     * Map Observation to ObservationDTO
     *
     * @param observation Observation
     * @return ObservationDTO
     */
    public static ObservationDTO mapToObservationDTO(Observation observation) {
        ObservationDTO observationDTO = new ObservationDTO();
        observationDTO.setObservationId(observation.getObservationId());
        observationDTO.setObservationEnum(ObservationEnum.getByObservationId(observation.getObservationTypeId()));
        observationDTO.setPatientDTO(PatientMapper.mapToPatientDTO(observation.getPatient()));
        observationDTO.setObservationDateTime(observation.getObservationDate());
        observationDTO.setObservationValue(observation.getObservationValue());
        return observationDTO;
    }

    /**
     * Map ObservationDTO to Observation
     *
     * @param observationDTO ObservationDTO
     * @return Observation
     */
    public static Observation mapToObservation(ObservationDTO observationDTO) {
        Observation observation = new Observation();
        if (observationDTO.getObservationId() != null) {
            observation.setObservationId(observationDTO.getObservationId());
        } else {
            observation.setObservationId(0L);
        }
        if (observationDTO.getObservationDateTime() != null) {
            observation.setObservationDate(observationDTO.getObservationDateTime());
        }
        if (observationDTO.getObservationEnum() != null) {
            observation.setObservationTypeId(observationDTO.getObservationEnum().getObservationEnumId());
        }
        observation.setPatient(PatientMapper.mapToPatient(observationDTO.getPatientDTO()));
        observation.setObservationDate(getObservationDateTime(observationDTO.getObservationDateTime()));
        observation.setObservationValue(observationDTO.getObservationValue());
        return observation;
    }

    /**
     * Format observationDateTime
     *
     * @param observationDateTime LocalDateTime
     * @return LocalDateTime
     */
    public static LocalDateTime getObservationDateTime(LocalDateTime observationDateTime) {
        LocalDateTime locateDateTime = observationDateTime;
        if (locateDateTime == null) {
            locateDateTime = LocalDateTime.now();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = locateDateTime.format(formatter);
        return LocalDateTime.parse(formattedDateTime, formatter);
    }

    public static ErrorInfoResponse getErrorResponse(String message, String errorMessage, int value) {
        ErrorInfoResponse errorInfoResponse = new ErrorInfoResponse();
        errorInfoResponse.setMessage(message);
        errorInfoResponse.setError(errorMessage);
        errorInfoResponse.setTimestamp(getObservationDateTime(LocalDateTime.now()).toString());
        errorInfoResponse.setStatus(value);
        return errorInfoResponse;
    }
}