/*
 * Copyright 2019 <Ciupitu Dennis-Mircea 323CA>
 */
package queue;

import java.util.ArrayList;

import emergency.InvestigationResult;
import entities.Doctor;
import entities.Patient;

import outputWrite.PrintRound;

public class Hospital {
    private ArrayList<Patient> hospitalizedPatients;
    private ArrayList<Doctor> doctors;
    private static final int MAGIC22 = 22;

    public Hospital() {
        this.hospitalizedPatients = new ArrayList<Patient>();
        this.doctors = new ArrayList<Doctor>();
    }

    // functie de sortare alfabetica a pacientilor
    public final void sort() {
        for (int i = 0; i < hospitalizedPatients.size() - 1; i++) {
            for (int j = i + 1; j < hospitalizedPatients.size(); j++) {
                if (hospitalizedPatients.get(i).getName().
                        compareTo(hospitalizedPatients.get(j).getName()) > 0) {
                    Patient patient = hospitalizedPatients.get(i);
                    hospitalizedPatients.set(i, hospitalizedPatients.get(j));
                    hospitalizedPatients.set(j, patient);
                }
            }
        }
    }

    // functie pentru ingrijirea pacientilor spitalizati de catre asistente
    public final void printNurses(final PrintRound round, final int nurses) {
        for (int i = 0; i < hospitalizedPatients.size(); i++) {
            // scade rundele sub tratament ale pacientului spitalizat
            hospitalizedPatients.get(i).decRoundsUnderTreatment();

            // seteaza noua severitate scazand factorul T
            hospitalizedPatients.get(i).setSeverity(hospitalizedPatients.get(i).getSeverity()
                                                                                - MAGIC22);

            // printam asistenta care ingrijeste pacientul curent
            if (hospitalizedPatients.get(i).getRoundsUnderTreatment() == 1) {
                round.addNursesContent("Nurse " + i % nurses + " treated "
                        + hospitalizedPatients.get(i).getName() + " and patient has "
                        + hospitalizedPatients.get(i).getRoundsUnderTreatment()
                        + " more round");
            } else {
                round.addNursesContent("Nurse " + i % nurses + " treated "
                        + hospitalizedPatients.get(i).getName() + " and patient has "
                        + hospitalizedPatients.get(i).getRoundsUnderTreatment()
                        + " more rounds");
            }
        }
    }
    // functie care da refresh pacientilor din lista unui doctor
    // in cazul in care s-a stabilit ca acesta trebuie sa plece acasa sau nu
    public final void refresh() {
        setState();
        for (int i = 0; i < hospitalizedPatients.size(); i++) {
            for (int j = 0; j < doctors.size(); j++) {
                if (hasPatient(doctors.get(j), hospitalizedPatients.get(i))) {
                    removePatient(doctors.get(j), hospitalizedPatients.get(i));
                    addPatient(doctors.get(j), hospitalizedPatients.get(i));
                }
            }
        }
    }

    // functie care stabileste ce pacient si-a terminat tratamentul
    // pentru a-l trimite dupa acasa
    public final void setState() {
        for (int i = 0; i < hospitalizedPatients.size(); i++) {
            if (hospitalizedPatients.get(i).getRoundsUnderTreatment() == 0
                || hospitalizedPatients.get(i).getSeverity() <= 0) {
                hospitalizedPatients.get(i).setState(InvestigationResult.HOME);
            }
        }
    }

    // functie care stabileste verdictul doctorilor la verificarea
    // pacientilor spitalizati
    public final void goHome(final PrintRound round) {
        for (int i = 0; i < doctors.size(); i++) {
            for (int j = 0; j < doctors.get(i).getPatients().size(); j++) {
                if (doctors.get(i).getPatients().get(j).getState().getValue().
                        equals(InvestigationResult.HOME.getValue())) {
                    round.addDoctorContent(doctors.get(i).getDoctorType().getValue()
                            + " sent " + doctors.get(i).getPatients().get(j).getName()
                            + " home");
                } else {
                    round.addDoctorContent(doctors.get(i).getDoctorType().getValue()
                            + " says that " + doctors.get(i).getPatients().get(j).getName()
                            + " should remain in hospital");
                }
            }
        }
    }

    // functie care trimite pacientii acasa dupa ce si-au terminat tratamentul
    public final void getOutPatient(final PrintRound round) {
        for (int i = hospitalizedPatients.size() - 1; i >= 0; i--) {
            if (hospitalizedPatients.get(i).getState().getValue().
                    equals(InvestigationResult.HOME.getValue())) {
                round.resetPatientContent(hospitalizedPatients.get(i).getName());
                round.addPatientContent(hospitalizedPatients.get(i).getName()
                        + " is sent home after treatment");
                // scoate pacientul din lista doctorului
                removePatient(hospitalizedPatients.get(i).getDoctors().get(0),
                                hospitalizedPatients.get(i));
                // scoate pacientul din lista pacientilor spitalizati
                hospitalizedPatients.remove(i);
            }
        }
    }

    // gettere si settere
    public final ArrayList<Patient> getPatients() {
        return this.hospitalizedPatients;
    }
    public final void addPatient(final Patient patient) {
        Patient newPatient = new Patient();
        newPatient.clone(patient);
        addPatient(newPatient.getDoctors().get(0), newPatient);
        this.hospitalizedPatients.add(newPatient);
    }

    public final void setDoctors(final ArrayList<Doctor> doctors) {
        for (int i = 0; i < doctors.size(); i++) {
            Doctor newDoctor = new Doctor();
            newDoctor.clone(doctors.get(i));
            this.doctors.add(newDoctor);
        }
    }
    public final ArrayList<Doctor> getDoctors() {
        return this.doctors;
    }
    // functie care adauga un pacient in lista unui doctor
    public final void addPatient(final Doctor doctor, final Patient patient) {
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).compare(doctor)) {
                doctors.get(i).addPatient(patient);
                break;
            }
        }
    }
    // functie care sterge un pacient din lista unui doctor
    public final void removePatient(final Doctor doctor, final Patient patient) {
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).compare(doctor)) {
                for (int j = 0; j < doctors.get(i).getPatients().size(); j++) {
                    if (doctors.get(i).getPatients().get(j).getName().equals(patient.getName())) {
                        doctors.get(i).getPatients().remove(j);
                        return;
                    }
                }
            }
        }
    }
    // functie care verifica daca un pacient este continut de un doctor
    public final boolean hasPatient(final Doctor doctor, final Patient patient) {
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).compare(doctor)) {
                for (int j = 0; j < doctors.get(i).getPatients().size(); j++) {
                    if (doctors.get(i).getPatients().get(j).getName().equals(patient.getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
