/*
 * Copyright 2019 <Ciupitu Dennis-Mircea 323CA>
 */
package entities;

// toate tipurile de doctor
public enum DoctorType {
    CARDIOLOGIST("Cardiologist"),
    ER_PHYSICIAN("ERPhysician"),
    GASTROENTEROLOGIST("Gastroenterologist"),
    GENERAL_SURGEON("General Surgeon"),
    INTERNIST("Internist"),
    NEUROLOGIST("Neurologist");

    private String value;

    DoctorType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
