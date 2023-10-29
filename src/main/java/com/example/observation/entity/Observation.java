package com.example.observation.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "observation")
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "observationid")
    private Long observationId;

    @Column(name = "observationtypeid")
    private Long observationTypeId;

    @ManyToOne
    @JoinColumn(name = "patientid")
    private Patient patient;

    @Column(name = "observationdate", columnDefinition = "TIMESTAMP")
    private LocalDateTime observationDate;

    @Column(name = "observationvalue")
    private Double observationValue;

    public Observation(Long observationId, Long observationTypeId, Patient patient,
                       LocalDateTime observationDate, Double observationValue) {
        super();
        this.observationId = observationId;
        this.observationTypeId = observationTypeId;
        this.patient = patient;
        this.observationDate = observationDate;
        this.observationValue = observationValue;
    }

    public Observation() {
    }

    public Long getObservationId() {
        return observationId;
    }

    public void setObservationId(Long observationId) {
        this.observationId = observationId;
    }

    public Long getObservationTypeId() {
        return observationTypeId;
    }

    public void setObservationTypeId(Long observationTypeId) {
        this.observationTypeId = observationTypeId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getObservationDate() {
        return observationDate;
    }

    public void setObservationDate(LocalDateTime observationDate) {
        this.observationDate = observationDate;
    }

    public Double getObservationValue() {
        return observationValue;
    }

    public void setObservationValue(Double observationValue) {
        this.observationValue = observationValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(observationDate, observationId, observationTypeId, observationValue, patient);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Observation other = (Observation) obj;
        return Objects.equals(observationDate, other.observationDate)
                && Objects.equals(observationId, other.observationId)
                && Objects.equals(observationTypeId, other.observationTypeId)
                && Objects.equals(observationValue, other.observationValue) && Objects.equals(patient, other.patient);
    }

}