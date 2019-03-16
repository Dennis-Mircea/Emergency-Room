/*
 * Copyright 2019 <Ciupitu Dennis-Mircea 323CA>
 */
package queue;

import java.util.Observable;
import java.util.ArrayList;

import emergency.InvestigationResult;
import emergency.Urgency;

import entities.Patient;

import outputWrite.PrintRound;

public class Examination extends Observable {
    private ArrayList<Patient> patients;
    private static final int THREE = 3;

    public Examination() {
        this.patients = new ArrayList<Patient>();
    }

    public final void update() {
        this.setChanged();
        this.notifyObservers();
    }

    // functie care verifica primul pacient din Examinations queue
    public final void execute(final Investigations investigations,
            final PrintRound round, final Hospital hospital) {

        // cat timp mai sunt pacienti in coada
        if (patients.size() != 0) {


            // daca nu a fost inca diagnosticat, va fi mutat in investigations queue
            if (patients.get(0).getState() == InvestigationResult.NOT_DIAGNOSED) {
                // daca severitatea depaseste constanta maxForTreatment a doctorului
                // pacientul esti adaugat in investigations queue
                if (patients.get(0).getSeverity()
                 > patients.get(0).getDoctors().get(0).getMaxFOrTreatment()) {

                    investigations.addPatient(patients.get(0));
                // altfel va fi trimis direct acasa
                } else {
                    round.addPatientContent(patients.get(0).getName() + " is sent home by "
                            + patients.get(0).getDoctors().get(0).
                            getDoctorType().getValue().toLowerCase());
                }
            }

            // daca ER_Tehnicianul ii prescrie ca rezultat sa urmeze tratament
            // pacientul va fi trimis acasa
            if (patients.get(0).getState() == InvestigationResult.TREATMENT) {
                round.addPatientContent(patients.get(0).getName() + " is sent home by "
                        + patients.get(0).getDoctors().get(0).
                            getDoctorType().getValue().toLowerCase());
            }

            // daca pacientul trebuie spitalizat
            if (patients.get(0).getState() == InvestigationResult.HOSPITALIZE) {
             // asta se face doar o singura data, adica prima data cand vine de la investigator
                if (patients.get(0).getRoundsUnderTreatment() == 0) {

                    // calculeaza numarul de runde in care pacientul va sta sub tratament
                    // dupa formula max(S * C1, 3)
                    int rounds = Math.round(patients.get(0).getSeverity()
                            * patients.get(0).getDoctors().get(0).getC1());
                    if (rounds > THREE) {
                        patients.get(0).setRoundsUnderTreatment(rounds);
                    } else {
                        patients.get(0).setRoundsUnderTreatment(THREE);
                    }

                    // adauga pacientul in lista de pacienti spitalizati
                    hospital.addPatient(patients.get(0));
                }
                // printeaza starea curenta a pacientului
                round.addPatientContent(patients.get(0).getName() + " is hospitalized by "
                        + patients.get(0).getDoctors().get(0).
                            getDoctorType().getValue().toLowerCase());

            }
            // daca pacientul trebuie operat
            if (patients.get(0).getState().getValue().
                    equals(InvestigationResult.OPERATE.getValue())) {
                // verifica daca e un doctor chirurg disponibil
                if (patients.get(0).getRoundsUnderTreatment() == 0) {

                    boolean isAvailable = false;
                    for (int i = 0; i < patients.get(0).getDoctors().size(); i++) {
                        // daca doctorul este chirurg atunci va trece pe prima pozitie in lista
                        // de doctori a pacientului
                        if (patients.get(0).getDoctors().get(i).getIsSurgeon()) {
                            patients.get(0).getDoctors().add(0,
                                    patients.get(0).getDoctors().get(i));
                            patients.get(0).getDoctors().remove(i);
                            isAvailable = true;
                            break;
                        }

                    }
                    // daca nu exista niciun doctor chirurg pacientul este trasferant
                    if (!isAvailable) {
                        round.addPatientContent(patients.get(0).getName()
                                + " is transferred to other hospital");
                        patients.get(0).setState(InvestigationResult.TRANSFERED);
                        return;
                    }

                    // calculam noua severitate dupa formula S = S - S*C2
                    int newSeverity = Math.round((float) patients.get(0).getSeverity()
                        -  Math.round(patients.get(0).getDoctors().get(0).getC2()
                        * (float) patients.get(0).getSeverity()));

                    patients.get(0).setSeverity(newSeverity);
                    // calculam runde in care pacientul va sta sub tratament
                    int rounds = Math.round(newSeverity
                            * patients.get(0).getDoctors().get(0).getC1());
                    if (rounds > THREE) {
                        patients.get(0).setRoundsUnderTreatment(rounds);
                    } else {
                        patients.get(0).setRoundsUnderTreatment(THREE);
                    }

                    // adaugam pacientul in lista de pacienti spitalizati
                    hospital.addPatient(patients.get(0));
                }

                // printeaza starea curenta a pacientului
                round.addPatientContent(patients.get(0).getName() + " is operated by "
                        + patients.get(0).getDoctors().get(0).
                        getDoctorType().getValue().toLowerCase());
            }
        }
    }
    // gettere si settere
    public final void addPatient(final Patient patient) {
        patients.add(patient);
    }
    public final ArrayList<Patient> getPatients() {
        return this.patients;
    }
    // functie pentru formare cozii de examinare
    public final void setPatients(final Triage triageQueue) {
        // adauga pacientii din triage queue conform numarului de asistente
        // si a numarului de pacienti deja aflati in coada
        if (triageQueue.getNurses() > triageQueue.getPatients().size()) {
            for (int i = triageQueue.getPatients().size() - 1; i >= 0; i--) {
                patients.add(triageQueue.getPatients().get(i));
                // dupa ce un pacient a fost adaugat, acesta este scos din triaj
                triageQueue.getPatients().remove(i);
            }
        } else {
            for (int i = triageQueue.getNurses() - 1; i >= 0; i--) {
              patients.add(triageQueue.getPatients().get(i));
           // dupa ce un pacient a fost adaugat, acesta este scos din triaj
              triageQueue.getPatients().remove(i);
          }
        }
        // dupa ce au fost adaugati pacientii se realizeaza o sortare in functie
        // de gradul de urgenta si de severitate
        sort();
    }

    // printeaza pacientii ramasi in Examinations Queue
    public final void printExistedPatients(final PrintRound round) {
        for (int i = 0; i < patients.size(); i++) {
            round.addPatientContent(patients.get(i).getName() + " is in examinations queue");
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
