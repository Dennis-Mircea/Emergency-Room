/*
 * Copyright 2019 <Ciupitu Dennis-Mircea 323CA>
 */
package entities;

import java.util.ArrayList;

import emergency.IllnessType;
import emergency.Urgency;
import emergency.UrgencyEstimator;
import emergency.InvestigationResult;
import emergency.EmergencyRoom;

public class Patient {
    private long id;    // id-ul pacientului
    private long age;   // varsta pacientului
    private long time;  // timpul cand pacientul va intra in simulare
    private String name;    // numele pacientului
    private IllnessType illnessName;    // boala pe care o are pacientului
    private long severity;  // severitatea afectiunii pacientului
    private Urgency urgencyName;    // urgenta stabilita in triaj
    private ArrayList<Doctor> doctors;  // lista de doctori care pot trata afectiunea
    private InvestigationResult state;  // rezultatul in urma investigarii
    private int roundsUnderTreatment;   // numarul de runde sub tratament

    public Patient() {
        this.doctors = new ArrayList<Doctor>();
        this.state = InvestigationResult.NOT_DIAGNOSED;
    }

    // functie care seteaza pentru un pacient toti doctorii care se pot ocupa
    // de afectiunea acestuia
    public final void setDoctors(final EmergencyRoom room) {
        for (int i = 0; i < room.getDoctors().size(); i++) {
            for (int j = 0; j < room.getDoctors().get(i).getIllness().size(); j++) {
                if (this.illnessName == room.getDoctors().get(i).getIllness().get(j)) {
                    this.doctors.add(room.getDoctors().get(i));
                    break;
                }
            }
        }
    }

    // gettere si settere
    public final void setId(final long id) {
        this.id = id;
    }
    public final long getId() {
        return this.id;
    }
    public final void setAge(final long age) {
        this.age = age;
    }
    public final long getAge() {
        return this.age;
    }
    public final void setTime(final long time) {
        this.time = time;
    }
    public final void decTime() {
        this.time--;
    }
    public final long getTime() {
        return this.time;
    }
    public final void setName(final String name) {
        this.name = name;
    }
    public final String getName() {
        return this.name;
    }
    public final void setIllnessType(final IllnessType type) {
        this.illnessName = type;
    }
    public final IllnessType getIllnessType() {
        return this.illnessName;
    }
    public final void setSeverity(final long severity) {
        this.severity = severity;
    }
    public final long getSeverity() {
        return this.severity;
    }
    public final void setUrgency() {
        UrgencyEstimator urgency = UrgencyEstimator.getInstance();
        this.urgencyName = urgency.estimateUrgency(illnessName, (int) severity);
    }
    public final Urgency getUrgency() {
        return this.urgencyName;
    }
    public final void setState(final InvestigationResult state) {
        this.state = state;
    }
    public final InvestigationResult getState() {
        return this.state;
    }
    public final ArrayList<Doctor> getDoctors() {
        return this.doctors;
    }
    public final void setRoundsUnderTreatment(final int round) {
        this.roundsUnderTreatment = round;
    }
    public final void decRoundsUnderTreatment() {
        this.roundsUnderTreatment--;
    }
    public final int getRoundsUnderTreatment() {
        return this.roundsUnderTreatment;
    }
    public final void addDoctor(final Doctor doctor) {
        this.doctors.add(doctor);
    }
    // cloneaza entitatea pacient
    public final void clone(final Patient patient) {
        this.id = patient.id;
        this.age = patient.age;
        this.time = patient.time;
        this.illnessName = patient.illnessName;
        this.name = patient.name;
        this.severity = patient.severity;
        this.urgencyName = patient.urgencyName;
        this.state = patient.state;
        this.roundsUnderTreatment = patient.roundsUnderTreatment;
        for (int i = 0; i < patient.getDoctors().size(); i++) {
            this.doctors.add(patient.getDoctors().get(i));
        }
    }
    // functie care compara doi pacienti
    public final boolean compare(final Patient patient) {
        if (this.name.equals(patient.getName())) {
            return true;
        }
        return false;
    }
}
