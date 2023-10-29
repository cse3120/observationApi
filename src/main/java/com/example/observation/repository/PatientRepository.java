package com.example.observation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.observation.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {


}
