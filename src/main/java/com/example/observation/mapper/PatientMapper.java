package com.example.observation.mapper;

import com.example.observation.dto.PatientDTO;
import com.example.observation.entity.Patient;

public class PatientMapper {

    /**
     * Map Patient to PatientDTO
     *
     * @param patient Patient
     * @return PatientDTO
     */
    public static PatientDTO mapToPatientDTO(Patient patient) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setPatientId(patient.getPatientId());
        patientDTO.setPatientName(patient.getPatientName());
        return patientDTO;
    }

    /**
     * Map PatientDTO to Patient
     *
     * @param patientDTO PatientDTO
     * @return Patient
     */
    public static Patient mapToPatient(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setPatientId(patientDTO.getPatientId());
        patient.setPatientName(patientDTO.getPatientName());
        return patient;
    }

}