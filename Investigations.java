/*
 * Copyright 2019 <Ciupitu Dennis-Mircea 323CA>
 */
package queue;

import java.util.ArrayList;

import emergency.InvestigationResult;
import emergency.Urgency;
import entities.Doctor;
import entities.Patient;

import outputWrite.PrintRound;

public class Investigations {
    private int investigators;
    private ArrayList<Patient> patients;
    public static final int C1 = 75;
    public static final int C2 = 40;

    public Investigations(final int investigators) {
        this.patients = new ArrayList<Patient>();
        this.investigators = investigators;
    }

    // seteaza rezultatul investigarii
    public final void setResult() {
        int tehnicians = investigators;
        for (int i = 0; i < patients.size(); i++) {
            // cat timp mai sunt investigatori liberi, verifica pacienti
            if (tehnicians == 0) {
                break;
            }
            // compara severitatea cu cele 2 constante pentru a stabili rezultatul
            if (patients.get(i).getSeverity() > C1) {
                patients.get(i).setState(InvestigationResult.OPERATE);
            } else {
                if (patients.get(i).getSeverity() <= C2) {
                    patients.get(i).setState(InvestigationResult.TREATMENT);
                } else {
                    patients.get(i).setState(InvestigationResult.HOSPITALIZE);
                }
            }
            tehnicians--;
        }
    }
    // functie care trimite pacientii inapoi in examinations queue dupa ce au fost diagnosticati
    // in functie de numarul de ERTehnicieni disponibili
    public final void execute(final Examination examination) {
        int tehnicians = investigators;
        while (patients.size() != 0 && tehnicians != 0) {
            examination.addPatient(patients.get(0));
            patients.remove(0);
            tehnicians--;
        }
    }
    // functie care printeaza pacientii ramasi in urma trimiterii in examinations queue
    // daca este cazul
    public final void printExistedPatient(final PrintRound round) {
        if (patients.size() != 0) {
            for (int i = 0; i < patients.size(); i++) {
                round.addPatientContent(patients.get(i).getName() + " is in investigations queue");
            }
        }
    }

    // gettere si settere
    public final void addPatient(final Patient patient) {
        Patient newPatient = new Patient();
        newPatient.clone(patient);
        patients.add(newPatient);
    }
    public final ArrayList<Patient> getPatients() {
        return this.patients;
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
    // functie pentru sortate in functie de urgenta si severitate
    public final void sort() {
        for (int i = 0; i < patients.size() - 1; i++) {
            for (int j = i + 1; j < patients.size(); j++) {
                if (patients.get(j).getUrgency() == Urgency.IMMEDIATE
                        && patients.get(i).getUrgency() != Urgency.IMMEDIATE) {
                    Patient aux = patients.get(i);
                    Patient aux2 = patients.get(j);
                    patients.set(i, aux2);
                    patients.set(j, aux);

                }
                if (patients.get(j).getUrgency() == Urgency.IMMEDIATE
                        && patients.get(i).getUrgency() == Urgency.IMMEDIATE) {
                    if (patients.get(j).getSeverity() > patients.get(i).getSeverity()) {
                        Patient aux = patients.get(i);
                        Patient aux2 = patients.get(j);
                        patients.set(i, aux2);
                        patients.set(j, aux);
                    }
                    if (patients.get(j).getSeverity() == patients.get(i).getSeverity()) {
                        if (patients.get(j).getName().compareTo(patients.get(i).getName()) > 0) {
                            Patient aux = patients.get(i);
                            Patient aux2 = patients.get(j);
                            patients.set(i, aux2);
                            patients.set(j, aux);
                        }
                    }
                }
                if (patients.get(j).getUrgency() == Urgency.URGENT
                        && patients.get(i).getUrgency() != Urgency.IMMEDIATE
                        && patients.get(i).getUrgency() != Urgency.URGENT) {
                    Patient aux = patients.get(i);
                    Patient aux2 = patients.get(j);
                    patients.set(i, aux2);
                    patients.set(j, aux);

                }
                if (patients.get(j).getUrgency() == Urgency.URGENT
                        && patients.get(i).getUrgency() == Urgency.URGENT) {
                    if (patients.get(j).getSeverity() > patients.get(i).getSeverity()) {
                        Patient aux = patients.get(i);
                        Patient aux2 = patients.get(j);
                        patients.set(i, aux2);
                        patients.set(j, aux);

                    }
                    if (patients.get(j).getSeverity() == patients.get(i).getSeverity()) {
                        if (patients.get(j).getName().compareTo(patients.get(i).getName()) > 0) {
                            Patient aux = patients.get(i);
                            Patient aux2 = patients.get(j);
                            patients.set(i, aux2);
                            patients.set(j, aux);
                        }
                    }
                }
                if (patients.get(j).getUrgency() == Urgency.LESS_URGENT
                        && patients.get(i).getUrgency() == Urgency.LESS_URGENT) {
                    if (patients.get(j).getSeverity() > patients.get(i).getSeverity()) {
                        Patient aux = patients.get(i);
                        Patient aux2 = patients.get(j);
                        patients.set(i, aux2);
                        patients.set(j, aux);
                    }
                    if (patients.get(j).getSeverity() == patients.get(i).getSeverity()) {
                        if (patients.get(j).getName().compareTo(patients.get(i).getName()) > 0) {
                            Patient aux = patients.get(i);
                            Patient aux2 = patients.get(j);
                            patients.set(i, aux2);
                            patients.set(j, aux);
                        }
                    }
                }
                if (patients.get(j).getUrgency() == Urgency.LESS_URGENT
                        && patients.get(i).getUrgency() != Urgency.IMMEDIATE
                        && patients.get(i).getUrgency() != Urgency.URGENT
                        && patients.get(i).getUrgency() != Urgency.LESS_URGENT) {
                    Patient aux = patients.get(i);
                    Patient aux2 = patients.get(j);
                    patients.set(i, aux2);
                    patients.set(j, aux);

                }
                if (patients.get(j).getUrgency() == Urgency.NON_URGENT
                        && patients.get(i).getUrgency() == Urgency.NON_URGENT) {
                    if (patients.get(j).getSeverity() > patients.get(i).getSeverity()) {
                        Patient aux = patients.get(i);
                        Patient aux2 = patients.get(j);
                        patients.set(i, aux2);
                        patients.set(j, aux);
                    }
                    if (patients.get(j).getSeverity() == patients.get(i).getSeverity()) {
                        if (patients.get(j).getName().compareTo(patients.get(i).getName()) > 0) {
                            Patient aux = patients.get(i);
                            Patient aux2 = patients.get(j);
                            patients.set(i, aux2);
                            patients.set(j, aux);
                        }
                    }
                }
            }
        }
    }
}
