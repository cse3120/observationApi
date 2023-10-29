package com.example.observation.dto;

public enum ObservationEnum {

    HEART_RATE(1L, "heart-rate", "beats/minute"),

    SKIN_TEMPERATURE(2L, "skin-temperature", "degrees Celsius"),

    RESPIRATORY_RATE(3L, "respiratory-rate", "breaths/minute");

    private final long observationEnumId;

    private final String observationTypeName;

    private final String observationTypeUnit;

    ObservationEnum(long observationEnumId, String observationTypeName, String observationTypeUnit) {
        this.observationEnumId = observationEnumId;
        this.observationTypeName = observationTypeName;
        this.observationTypeUnit = observationTypeUnit;
    }

    public long getObservationEnumId() {
        return observationEnumId;
    }

    public String getObservationTypeName() {
        return observationTypeName;
    }

    public String getObservationTypeUnit() {
        return observationTypeUnit;
    }

    /**
     * Helper method to retrieve an enum by observationTypeName
     *
     * @param observationTypeName String
     * @return ObservationEnum
     */
    public static ObservationEnum getByObservationTypeName(String observationTypeName) {
        for (ObservationEnum observationEnum : ObservationEnum.values()) {
            if (observationEnum.getObservationTypeName().equalsIgnoreCase(observationTypeName)) {
                return observationEnum;
            }
        }
        throw new IllegalArgumentException("No enum constant with name: " + observationTypeName);
    }

    /**
     * Helper method to retrieve an enum by observationTypeUnit
     *
     * @param observationTypeUnit String
     * @return ObservationEnum
     */
    public static ObservationEnum getByObservationTypeUnit(String observationTypeUnit) {
        for (ObservationEnum observationEnum : ObservationEnum.values()) {
            if (observationEnum.getObservationTypeUnit().equalsIgnoreCase(observationTypeUnit)) {
                return observationEnum;
            }
        }
        throw new IllegalArgumentException("No enum constant with code: " + observationTypeUnit);
    }

    /**
     * Helper method to retrieve an enum by observationEnumId
     *
     * @param observationEnumId long
     * @return ObservationEnum
     */
    public static ObservationEnum getByObservationId(long observationEnumId) {
        for (ObservationEnum observationEnum : ObservationEnum.values()) {
            if (observationEnum.getObservationEnumId() == observationEnumId) {
                return observationEnum;
            }
        }
        throw new IllegalArgumentException("No enum constant with id: " + observationEnumId);
    }
}