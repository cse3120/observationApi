package com.example.observation;

import com.example.observation.dto.ObservationDTO;
import com.example.observation.dto.ObservationEnum;
import com.example.observation.dto.PatientDTO;
import com.example.observation.response.ObservationResponse;
import com.example.observation.service.ObservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class ObservationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ObservationService observationService;

    List<ObservationDTO> observationDTOList;
    ObservationDTO observationDTO;
    ObservationDTO observationDTO2;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private DateTimeFormatter dateTimeFormatter;
    PatientDTO patientDTO;
    List<ObservationResponse> observationResponseList=null;

    @BeforeEach
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        observationDTOList = new ArrayList<>();
        PatientDTO patientDTO = new PatientDTO(1L, "Tom");
        PatientDTO patientDTO1 = new PatientDTO(2L, "Chris");
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        observationDTO = new ObservationDTO(1L, ObservationEnum.HEART_RATE, patientDTO, LocalDateTime.parse("2023-09-06T11:02:44Z", dateTimeFormatter), 65.0);
        observationDTO2 = new ObservationDTO(2L, ObservationEnum.RESPIRATORY_RATE, patientDTO1, LocalDateTime.parse("2023-09-07T11:23:24Z", dateTimeFormatter), 37.2);
        observationDTOList.add(observationDTO);
        observationDTOList.add(observationDTO2);
        patientDTO = new PatientDTO();
        patientDTO.setPatientId(2L);
        patientDTO.setPatientName("Chris");

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
    }

    @Test
    void testGetAllObservations() throws Exception {
        when(observationService.getAllObservations()).thenReturn(observationResponseList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/observations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testCreateObservation() throws Exception {
        when(observationService.createObservation(observationDTO)).thenReturn(observationDTO);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/observations")
                                .content(objectMapper.writeValueAsString(observationDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.observationId", is(1)));
    }

    @Test
    void testModifyObservation() throws Exception {
        ObservationDTO updatedObservationDTO = new ObservationDTO(2L, ObservationEnum.RESPIRATORY_RATE, patientDTO, LocalDateTime.parse("2023-09-07T11:02:44Z", dateTimeFormatter), 15.0);
        when(observationService.getObservationById(2)).thenReturn(Optional.of(observationDTO2));
        when(observationService.modifyObservation(observationDTO2)).thenReturn(updatedObservationDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/observations/{id}", 2)
                        .content(objectMapper.writeValueAsString(updatedObservationDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.observationValue", is(15.0)));
    }

    @Test
    void testModifyObservationReturnNotFound() throws Exception {
        ObservationDTO updatedObservationDTO = new ObservationDTO(2L, ObservationEnum.RESPIRATORY_RATE, patientDTO, LocalDateTime.parse("2023-09-07T11:02:44Z", dateTimeFormatter), 15.0);
        when(observationService.getObservationById(2)).thenReturn(Optional.empty());
        when(observationService.modifyObservation(any(ObservationDTO.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/observations/{id}", 2)
                        .content(objectMapper.writeValueAsString(updatedObservationDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteObservation() throws Exception {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setPatientId(3L);
        patientDTO.setPatientName("Jim");
        ObservationDTO observationDTO3 = new ObservationDTO(3L, ObservationEnum.SKIN_TEMPERATURE, patientDTO, LocalDateTime.parse("2023-09-07T11:02:44Z", dateTimeFormatter), 37.8);
        when(observationService.getObservationById(3)).thenReturn(Optional.of(observationDTO3));
        when(observationService.deleteObservation(3)).thenReturn(ResponseEntity.ok().body("Observation model is deleted for observationId 3"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/observations/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}