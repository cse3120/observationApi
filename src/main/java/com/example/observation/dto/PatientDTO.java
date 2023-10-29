package com.example.observation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class PatientDTO {
    @Schema(defaultValue = "1")
    private Long patientId;
    @Schema(defaultValue = "XXXX")
    private String patientName;

    public PatientDTO(Long patientId, String patientName) {
        this.patientId = patientId;
        this.patientName = patientName;
    }

    public PatientDTO() {
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, patientName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PatientDTO other = (PatientDTO) obj;
        return Objects.equals(patientId, other.patientId) && Objects.equals(patientName, other.patientName);
    }
}