package com.example.observation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Objects;

public class ObservationResponse {
    @Schema(defaultValue = "1")
    private Long observationId;
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

    public ObservationResponse(Long observationId,String observationType, LocalDateTime observationDateTime, Long patientId,
                               Double observationValue, String observationUnit) {
        super();
        this.observationId=observationId;
        this.observationType = observationType;
        this.observationDateTime = observationDateTime;
        this.patientId = patientId;
        this.observationValue = observationValue;
        this.observationUnit = observationUnit;
    }

    public Long getObservationId() {
        return observationId;
    }

    public void setObservationId(Long observationId) {
        this.observationId = observationId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObservationResponse that = (ObservationResponse) o;
        return Objects.equals(observationId, that.observationId) && Objects.equals(observationType, that.observationType) && Objects.equals(observationDateTime, that.observationDateTime) && Objects.equals(patientId, that.patientId) && Objects.equals(observationValue, that.observationValue) && Objects.equals(observationUnit, that.observationUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(observationId, observationType, observationDateTime, patientId, observationValue, observationUnit);
    }
}
