/*
 * Copyright 2019 <Ciupitu Dennis-Mircea 323CA>
 */
package emergency;

import java.util.ArrayList;
import java.util.Observable;

import entities.Doctor;
import entities.Patient;

public final class EmergencyRoom extends Observable {
    private long round;
    private long nurses;
    private long investigators;
    private ArrayList<Doctor> doctors;
    private ArrayList<Patient> patients;

    // Singleton pattern
    private static EmergencyRoom room = null;
    private EmergencyRoom() {
        this.doctors = new ArrayList<Doctor>();
        this.patients = new ArrayList<Patient>();
        this.round = 1;
    }

    public static EmergencyRoom getInstance() {
        if (room == null) {
            room = new EmergencyRoom();
        }
        return room;
    }

    public void update() {
        this.setChanged();
        this.notifyObservers();
    }

    // gettere si settere
    public void setRound(final long rounds) {
        this.round = rounds;
    }
    public long getRound() {
        return this.round;
    }
    public void incRound() {
        this.round++;
    }
    public void setNurses(final long nurses) {
        this.nurses = nurses;
    }
    public long getNurses() {
        return this.nurses;
    }
    public void setInvestigators(final long investigators) {
        this.investigators = investigators;
    }
    public long getInvestigators() {
        return this.investigators;
    }
    public void addDoctor(final Doctor doctor) {
        this.doctors.add(doctor);
    }
    public void addPatient(final Patient patient) {
        this.patients.add(patient);
    }
    public ArrayList<Doctor> getDoctors() {
        return this.doctors;
    }
    public ArrayList<Patient> getPatients() {
        return this.patients;
    }
    // seteaza pentru un pacient toti doctorii care pot trata afectiunea acestuia
    public void setDoctorsForPatients() {
        for (int i = 0; i < this.patients.size(); i++) {
            this.patients.get(i).setDoctors(this);
        }
    }
    // functie care pune un doctor la finalul cozii unui pacient
    public void getBackDoctor(final Doctor doctor) {
        for (int i = 0; i < patients.size(); i++) {
            for (int j = 0; j < patients.get(i).getDoctors().size() - 1; j++) {
                if (patients.get(i).getDoctors().get(j).compare(doctor)) {
                    patients.get(i).addDoctor(patients.get(i).getDoctors().get(j));
                    patients.get(i).getDoctors().remove(j);
                    break;
                }
            }
        }
    }
}
