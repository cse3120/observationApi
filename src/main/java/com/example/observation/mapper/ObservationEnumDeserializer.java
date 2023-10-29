package com.example.observation.mapper;

import com.example.observation.dto.ObservationEnum;
import com.example.observation.exception.ObservationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ObservationEnumDeserializer extends StdDeserializer<ObservationEnum> {
    public ObservationEnumDeserializer() {
        super(ObservationEnum.class);
    }

    @Override
    public ObservationEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String enumValue = node.asText();
        if ("beats/minute".equals(enumValue) || "heart-rate".equals(enumValue)) {
            return ObservationEnum.HEART_RATE;
        }
        if ("degrees Celsius".equals(enumValue) || "skin-temperature".equals(enumValue)) {
            return ObservationEnum.SKIN_TEMPERATURE;
        }
        if ("breaths/minute".equals(enumValue) || "respiratory-rate".equals(enumValue)) {
            return ObservationEnum.RESPIRATORY_RATE;
        }
        throw new ObservationException(" ObservationType is not available");
    }
    
}
