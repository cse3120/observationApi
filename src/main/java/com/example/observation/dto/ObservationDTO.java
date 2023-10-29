package com.example.observation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

public class ObservationDTO {

    @Schema(defaultValue = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long observationId;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, defaultValue = "2023-09-27T20:23:14")
    private LocalDateTime observationDateTime;

    @NotNull
    @Schema(defaultValue = "65")
    private Double observationValue;

    @NotNull
    @JsonProperty("observationType")
    @Schema(defaultValue = "heart-rate")
    private ObservationEnum observationEnum;

    @NotNull
    @JsonProperty("patient")
    private PatientDTO patientDTO;

    public ObservationDTO(Long observationId, ObservationEnum observationEnum, PatientDTO patientDTO, LocalDateTime observationDateTime, Double observationValue) {
        this.observationId = observationId;
        this.observationEnum = observationEnum;
        this.patientDTO = patientDTO;
        this.observationDateTime = observationDateTime;
        this.observationValue = observationValue;
    }

    public ObservationDTO() {
    }

    public Long getObservationId() {
        return observationId;
    }

    public void setObservationId(Long observationId) {
        this.observationId = observationId;
    }

    public LocalDateTime getObservationDateTime() {
        return observationDateTime;
    }

    public void setObservationDateTime(LocalDateTime observationDateTime) {
        this.observationDateTime = observationDateTime;
    }

    public Double getObservationValue() {
        return observationValue;
    }

    public void setObservationValue(Double observationValue) {
        this.observationValue = observationValue;
    }

    public ObservationEnum getObservationEnum() {
        return observationEnum;
    }

    public void setObservationEnum(ObservationEnum observationEnum) {
        this.observationEnum = observationEnum;
    }

    public PatientDTO getPatientDTO() {
        return patientDTO;
    }

    public void setPatientDTO(PatientDTO patientDTO) {
        this.patientDTO = patientDTO;
    }

    @Override
    public int hashCode() {
        return Objects.hash(observationDateTime, observationEnum, observationId, observationValue, patientDTO);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ObservationDTO other = (ObservationDTO) obj;
        return Objects.equals(observationDateTime, other.observationDateTime) && observationEnum == other.observationEnum
                && Objects.equals(observationId, other.observationId)
                && Objects.equals(observationValue, other.observationValue)
                && Objects.equals(patientDTO, other.patientDTO);
    }
}