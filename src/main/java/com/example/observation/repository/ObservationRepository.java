package com.example.observation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.observation.entity.Observation;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long>{

}
