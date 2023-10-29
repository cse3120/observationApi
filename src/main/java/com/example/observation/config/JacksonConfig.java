package com.example.observation.config;

import com.example.observation.dto.ObservationEnum;
import com.example.observation.mapper.ObservationEnumDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(ObservationEnum.class, new ObservationEnumDeserializer());
        objectMapper.registerModule(simpleModule);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
