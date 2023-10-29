package com.example.observation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Objects;

public class ObservationResponse {
    @Schema(defaultValue = "heart-rate")
    private String observationType;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime observationDateTime;
    @Schema(defaultValue = "1")
    private Long patientId;
    @Schema(defaultValue = "65.0")
    private Double observationValue;
    @Schema(defaultValue = "beats/minute")
    private String observationUnit;

    public ObservationResponse() {
    }

    public ObservationResponse(String observationType, LocalDateTime observationDateTime, Long patientId,
                               Double observationValue, String observationUnit) {
        super();
        this.observationType = observationType;
        this.observationDateTime = observationDateTime;
        this.patientId = patientId;
        this.observationValue = observationValue;
        this.observationUnit = observationUnit;
    }

    public String getObservationType() {
        return observationType;
    }

    public void setObservationType(String observationType) {
        this.observationType = observationType;
    }

    public LocalDateTime getObservationDateTime() {
        return observationDateTime;
    }

    public void setObservationDateTime(LocalDateTime observationDateTime) {
        this.observationDateTime = observationDateTime;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Double getObservationValue() {
        return observationValue;
    }

    public void setObservationValue(Double observationValue) {
        this.observationValue = observationValue;
    }

    public String getObservationUnit() {
        return observationUnit;
    }

    public void setObservationUnit(String observationUnit) {
        this.observationUnit = observationUnit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(observationDateTime, observationType, observationUnit, observationValue, patientId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ObservationResponse other = (ObservationResponse) obj;
        return Objects.equals(observationDateTime, other.observationDateTime)
                && Objects.equals(observationType, other.observationType)
                && Objects.equals(observationUnit, other.observationUnit)
                && Objects.equals(observationValue, other.observationValue)
                && Objects.equals(patientId, other.patientId);
    }
}