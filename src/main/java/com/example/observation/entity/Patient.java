package com.example.observation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @Column(name = "patientid")
    private Long patientId;

    @Column(name = "patientname")
    private String patientName;

    public Patient(Long patientId, String patientName) {
        super();
        this.patientId = patientId;
        this.patientName = patientName;
    }

    public Patient() {
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
        Patient other = (Patient) obj;
        return Objects.equals(patientId, other.patientId) && Objects.equals(patientName, other.patientName);
    }

}