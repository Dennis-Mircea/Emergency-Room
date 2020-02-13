/*
 * Copyright 2019 <Ciupitu Dennis-Mircea 323CA>
 */
package queue;

import java.util.ArrayList;

import entities.Doctor;
import entities.Patient;

import outputWrite.PrintRound;

public class Triage {
    private int nurses;
    private int initialSize;
    private ArrayList<Patient> patients;

    public Triage() {
        this.patients = new ArrayList<Patient>();
    }

    // functie care sorteaza pacientii dupa severitate si le stabileste gradul de urgenta
    public final void execute() {
        setUrgency();
        for (int i = 0; i < patients.size() - 1; i++) {
            for (int j = i + 1; j < patients.size(); j++) {
                if (patients.get(j).getSeverity() > patients.get(i).getSeverity()) {
                    Patient aux = patients.get(i);
                    Patient aux2 = patients.get(j);
                    patients.set(i, aux2);
                    patients.set(j, aux);
                }
            }
        }
    }

    // goleste TriageQueue dupa ce s-au transferat toti pacientii in examination
    public final void clear() {
        for (int i = 0; i < initialSize; i++) {
            patients.remove(0);
        }
        initialSize = 0;
    }
    // printeaza pacientii ramas in urma trimiterii lor in examinations
    public final void printExistedPatients(final PrintRound round) {
        for (int i = 0; i < patients.size(); i++) {
            round.addPatientContent(patients.get(i).getName() + " is in triage queue");
        }
    }
    // functie care pune un doctor la finalul cozii unui pacient
    public final void getBackDoctor(final Doctor doctor) {
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
    // gettere si settere
    // functie care adauga pacienti in triaj in functie de runda in care acestia
    // trebuie sa intre
    public final void setPatients(final ArrayList<Patient> newPatients, final int round) {
        while (newPatients.size() != 0) {
            if (newPatients.get(0).getTime() == round - 1) {
                this.patients.add(newPatients.get(0));
                // odata ce un pacient este adaugat in triaj acesta este scos din
                // emergency room
                newPatients.remove(0);
            } else {
                break;
            }
        }
        if (this.patients.size() > nurses) {
            initialSize = nurses;
        } else {
            initialSize = this.patients.size();
        }
    }
    public final ArrayList<Patient> getPatients() {
        return this.patients;
    }
    public final void addPatient(final Patient patient) {
        patients.add(patient);
    }
    public final void setNurses(final int nurses) {
        this.nurses = nurses;
    }
    public final int getNurses() {
        return this.nurses;
    }
    public final void setUrgency() {
        for (int i = 0; i < patients.size(); i++) {
            patients.get(i).setUrgency();
        }
    }
    public final int getInitialSize() {
        return this.initialSize;
    }
}
