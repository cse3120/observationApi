package com.example.observation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.observation.response.ObservationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.observation.dto.ObservationDTO;
import com.example.observation.dto.ObservationEnum;
import com.example.observation.dto.PatientDTO;
import com.example.observation.entity.Observation;
import com.example.observation.entity.Patient;
import com.example.observation.repository.ObservationRepository;
import com.example.observation.repository.PatientRepository;
import com.example.observation.service.impl.ObservationServiceImpl;

@ExtendWith(SpringExtension.class)
public class ObservationServicesTest {

    @Mock
    ObservationRepository observationRepository;
    @Mock
    PatientRepository patientRepository;
    @InjectMocks
    ObservationServiceImpl observationServiceImpl;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

	List<Observation> observationList=null;
    List<ObservationResponse> observationResponseList=null;
	PatientDTO patientDTO=null;

    @BeforeEach
    public void setup() {
		observationList = new ArrayList<>();
		Patient patient1 = new Patient(1L, "Tom");
		Patient patient2 = new Patient(2L, "Chris");
		Observation observation1 = new Observation(1L, 1L, patient1,
				LocalDateTime.parse("2023-09-06T11:02:44Z", dateTimeFormatter), 65.0);
		Observation observation2 = new Observation(2L, 2L, patient2,
				LocalDateTime.parse("2023-09-07T11:23:24Z", dateTimeFormatter), 37.2);
		observationList.add(observation1);
		observationList.add(observation2);
		ObservationResponse observationResponse=new ObservationResponse();
		observationResponse.setObservationType(ObservationEnum.HEART_RATE.getObservationTypeName());
		observationResponse.setObservationUnit("beats/minute");
        observationResponse.setObservationDateTime(LocalDateTime.parse("2023-09-06T11:02:44Z", dateTimeFormatter));
        observationResponse.setObservationValue(65.0);
        observationResponse.setPatientId(1L);
        ObservationResponse observationResponse1=new ObservationResponse();
        observationResponse1.setObservationType(ObservationEnum.SKIN_TEMPERATURE.getObservationTypeName());
        observationResponse1.setObservationUnit("degrees Celsius");
        observationResponse1.setObservationDateTime(LocalDateTime.parse("2023-09-07T11:23:24Z", dateTimeFormatter));
        observationResponse1.setObservationValue(37.2);
        observationResponse1.setPatientId(1L);
        observationResponseList=new ArrayList<>();
        observationResponseList.add(observationResponse);
        observationResponseList.add(observationResponse1);
		patientDTO = new PatientDTO();
		patientDTO.setPatientId(3L);
		patientDTO.setPatientName("Jim");
    }

    @Test
    void testGetObservationList() {
        when(observationRepository.findAll()).thenReturn(observationList);
        List<ObservationResponse> observationTypeDTOList = observationServiceImpl.getAllObservations();
        assertEquals(observationResponseList.size(), 2);
        assertEquals(observationResponseList.get(0).getObservationValue(), 65.0);
    }

    @Test
    void testCreateObservation1() {
        when(observationRepository.save(any(Observation.class)))
                .thenAnswer(invocation -> {
                    Observation savedObservation = invocation.getArgument(0);
                    savedObservation.setObservationId(3L);
                    return savedObservation;
                });
        when(patientRepository.save(any(Patient.class)))
                .thenAnswer(invocation -> {
                    Patient savedPatient = invocation.getArgument(0);
                    savedPatient.setPatientId(3L);
                    savedPatient.setPatientName("Jim");
                    return savedPatient;
                });

        ObservationDTO observationDTO = new ObservationDTO(3L, ObservationEnum.SKIN_TEMPERATURE, patientDTO,
                LocalDateTime.parse("2023-09-07T12:02:44Z", dateTimeFormatter), 65.0);
        ObservationDTO savedObservationDTO = observationServiceImpl.createObservation(observationDTO);
        assertNotNull(savedObservationDTO.getObservationId());
        assertEquals(savedObservationDTO.getPatientDTO().getPatientName(), "Jim");
    }

    @Test
    void testModifyObservation() {
        when(observationRepository.save(any(Observation.class)))
                .thenAnswer(invocation -> {
                    Observation observation = invocation.getArgument(0);
                    observation.setObservationId(3L);
                    observation.setObservationValue(45.3);
                    return observation;
                });
        when(patientRepository.save(any(Patient.class)))
                .thenAnswer(invocation -> {
                    Patient savedPatient = invocation.getArgument(0);
                    savedPatient.setPatientId(3L);
                    savedPatient.setPatientName("Jim");
                    return savedPatient;
                });
        ObservationDTO observationDTO = new ObservationDTO(3L, ObservationEnum.SKIN_TEMPERATURE, patientDTO,
                LocalDateTime.parse("2023-09-07T12:02:44Z", dateTimeFormatter), 45.3);
        ObservationDTO updatedObservationDTO = observationServiceImpl.modifyObservation(observationDTO);
        assertEquals(updatedObservationDTO.getObservationValue(), 45.3);
    }

    @Test
    void testDeleteObservation() {
        Patient patient1 = new Patient(1L, "Tom");
        Observation observation1 = new Observation(1L, 1L, patient1,
                LocalDateTime.parse("2023-09-06T11:02:44Z", dateTimeFormatter), 65.0);
        when(observationRepository.findById(1L)).thenReturn(Optional.of(observation1));
        observationServiceImpl.deleteObservation(observation1.getObservationId());
        verify(observationRepository, times(1)).delete(observation1);
    }
}