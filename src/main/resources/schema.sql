DROP schema IF EXISTS observations cascade;

create schema observations;

set schema 'observations';

DROP TABLE IF EXISTS ObservationType cascade;
DROP TABLE IF EXISTS Patient cascade;
DROP TABLE IF EXISTS Observation cascade;

CREATE TABLE ObservationType (
    observationTypeId SERIAL PRIMARY KEY,
    observationTypeName VARCHAR(50) NOT NULL,
	observationUnit VARCHAR(50) NOT NULL,
	CONSTRAINT observationTypeId_unique  UNIQUE (observationTypeId)
);

CREATE TABLE Patient (
    patientId INT PRIMARY KEY,
    patientName VARCHAR(100) NULL
);

CREATE TABLE Observation (
    observationId SERIAL UNIQUE PRIMARY KEY,
    observationTypeId INT NOT NULL REFERENCES ObservationType(observationTypeId),
	patientId INT NOT NULL REFERENCES Patient(patientId),
	observationDate TIMESTAMP NOT NULL,
	observationValue NUMERIC NOT NULL,
    CONSTRAINT observationId_unique  UNIQUE (observationId)
);

INSERT INTO ObservationType (observationTypeName, observationUnit) VALUES ('heart-rate', 'beats/minute'), ('skin-temperature', 'degrees Celsius'), ('respiratory-rate', 'breaths/minute');