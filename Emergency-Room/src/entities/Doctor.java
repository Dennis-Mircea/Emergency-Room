/*
 * Copyright 2019 <Ciupitu Dennis-Mircea 323CA>
 */
package entities;

import java.util.ArrayList;

import emergency.IllnessType;

public class Doctor {
    private DoctorType name;    // numele pacientului
    private boolean isSurgeon;  // daca este chirurg
    private long maxForTreatment;   // constanta maxForTreatment
    private ArrayList<IllnessType> illness; // lista de boli pe care le trateaza
    private float c1;   // constanta c1
    private float c2;   // constanta c2
    private static final int T = 22;    // factorul T
    private ArrayList<Patient> hospitalizesPatient; // lista de pacienti spitalizati
    private int id; // id-ul fiecarui doctor

    private static final float FLOAT01 = 0.01f, FLOAT1 = 0.1f, FLOAT2 = 0.2f,
            FLOAT3 = 0.3f, FLOAT4 = 0.4f, FLOAT5 = 0.5f;

    public Doctor() {
        this.illness = new ArrayList<IllnessType>();
        this.hospitalizesPatient = new ArrayList<Patient>();
    }

    // gettere si settere
    public final void setDoctorType(final DoctorType type) {
        this.name = type;
    }
    public final DoctorType getDoctorType() {
        return this.name;
    }
    public final void setIsSurgeon(final boolean isSurgeon) {
        if (isSurgeon) {
            this.isSurgeon = true;
        } else {
            this.isSurgeon = false;
        }
    }
    public final boolean getIsSurgeon() {
        return this.isSurgeon;
    }
    // pentru fiecare tip de doctor stabileste toate constantele din enunt
    public final void setIllness() {
        if (this.name == DoctorType.CARDIOLOGIST) {
            this.illness.add(IllnessType.HEART_ATTACK);
            this.illness.add(IllnessType.HEART_DISEASE);
            this.c1 = FLOAT4;
            this.c2 = FLOAT1;
        }
        if (this.name == DoctorType.ER_PHYSICIAN) {
            this.illness.add(IllnessType.ALLERGIC_REACTION);
            this.illness.add(IllnessType.BROKEN_BONES);
            this.illness.add(IllnessType.BURNS);
            this.illness.add(IllnessType.CAR_ACCIDENT);
            this.illness.add(IllnessType.CUTS);
            this.illness.add(IllnessType.HIGH_FEVER);
            this.illness.add(IllnessType.SPORT_INJURIES);
            this.c1 = FLOAT1;
            this.c2 = FLOAT3;
        }
        if (this.name == DoctorType.GASTROENTEROLOGIST) {
            this.illness.add(IllnessType.ABDOMINAL_PAIN);
            this.illness.add(IllnessType.ALLERGIC_REACTION);
            this.illness.add(IllnessType.FOOD_POISONING);
            this.c1 = FLOAT5;
            this.c2 = 0;
        }
        if (this.name == DoctorType.GENERAL_SURGEON) {
            this.illness.add(IllnessType.ABDOMINAL_PAIN);
            this.illness.add(IllnessType.BURNS);
            this.illness.add(IllnessType.CAR_ACCIDENT);
            this.illness.add(IllnessType.CUTS);
            this.illness.add(IllnessType.SPORT_INJURIES);
            this.c1 = FLOAT2;
            this.c2 = FLOAT2;
        }
        if (this.name == DoctorType.INTERNIST) {
            this.illness.add(IllnessType.ABDOMINAL_PAIN);
            this.illness.add(IllnessType.ALLERGIC_REACTION);
            this.illness.add(IllnessType.FOOD_POISONING);
            this.illness.add(IllnessType.HEART_DISEASE);
            this.illness.add(IllnessType.HIGH_FEVER);
            this.illness.add(IllnessType.PNEUMONIA);
            this.c1 = FLOAT01;
            this.c2 = 0;
        }
        if (this.name == DoctorType.NEUROLOGIST) {
            this.illness.add(IllnessType.STROKE);
            this.c1 = FLOAT5;
            this.c2 = FLOAT1;
        }
    }
    public final ArrayList<IllnessType> getIllness() {
        return this.illness;
    }
    public final void setMaxForTreatment(final long maxForTreatment) {
        this.maxForTreatment = maxForTreatment;
    }
    public final long getMaxFOrTreatment() {
        return this.maxForTreatment;
    }
    public final float getC1() {
        return this.c1;
    }
    public final float getC2() {
        return this.c2;
    }
    public static final int getT() {
        return T;
    }
    public final void addPatient(final Patient patient) {
        this.hospitalizesPatient.add(patient);
    }
    public final ArrayList<Patient> getPatients() {
        return this.hospitalizesPatient;
    }
    public final void setId(final int id) {
        this.id = id;
    }
    public final int getId() {
        return this.id;
    }
    // functie care compara doi doctori
    public final boolean compare(final Doctor doctor) {
        if (this.id == doctor.getId()) {
            return true;
        }
        return false;
    }
    // functie care cloneaza entitatea doctor
    public final Doctor clone(final Doctor doctor) {
        this.isSurgeon = doctor.getIsSurgeon();
        this.c1 = doctor.getC1();
        this.c2 = doctor.getC2();
        for (int i = 0; i < doctor.getPatients().size(); i++) {
            this.hospitalizesPatient.add(doctor.getPatients().get(i));
        }
        for (int i = 0; i < doctor.getIllness().size(); i++) {
            this.illness.add(doctor.getIllness().get(i));
        }
        this.maxForTreatment = doctor.getMaxFOrTreatment();
        this.name = doctor.getDoctorType();
        this.id = doctor.id;
        return this;
    }
}
