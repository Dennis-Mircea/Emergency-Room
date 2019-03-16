/*
 * Copyright 2019 <Ciupitu Dennis-Mircea 323CA>
 */
package outputWrite;

import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

import emergency.EmergencyRoom;

public class PrintRound implements Observer {
    private ArrayList<String> patientContent;   // continutul pentru pacienti
    private ArrayList<String> nursesContent;    // continutul pentru asistente
    private ArrayList<String> doctorContent;    // continutul pentru doctori

    public PrintRound() {
        patientContent = new ArrayList<String>();
        nursesContent = new ArrayList<String>();
        doctorContent = new ArrayList<String>();
    }

    // functie ca printeaza continutul unei runde
    public final void print(final long round) {
        // sorteaza toate continuturile pacientilor
        sorted();

        // printeaza fiecare continut in parte
        System.out.println("~~~~ Patients in round " + round + " ~~~~");
        for (int i = 0; i < patientContent.size(); i++) {
            System.out.println(patientContent.get(i));
        }
        System.out.println("");
        System.out.println("~~~~ Nurses treat patients ~~~~");
        for (int i = 0; i < nursesContent.size(); i++) {
            System.out.println(nursesContent.get(i));
        }
        System.out.println("");
        System.out.println("~~~~ Doctors check their hospitalized patients and give verdicts ~~~~");
        for (int i = 0; i < doctorContent.size(); i++) {
            System.out.println(doctorContent.get(i));
        }
        System.out.println("");
    }

    @Override
    public final void update(final Observable o, final Object arg) {
        EmergencyRoom room = (EmergencyRoom) o;

        // printeaza tot continutul unei runde
        print(room.getRound());

        // incrementeaza runda camerei de urgente
        room.incRound();

        // reseteaza continuturile
        reset();
    }

    // functie de sortare a continuturilor pacientilor in ordine alfabetica
    public final void sorted() {
        for (int i = 0; i < patientContent.size() - 1; i++) {
            for (int j = i + 1; j < patientContent.size(); j++) {
                if (patientContent.get(i).compareTo(patientContent.get(j)) >= 0) {
                    String aux = patientContent.get(i);
                    patientContent.set(i, patientContent.get(j));
                    patientContent.set(j, aux);
                }
            }
        }
    }
    // functie care reseteaza continuturile
    public final void reset() {
        // in cazul pacientilor se reseteaza continuturile doar daca
        // acestia s-au aflat anterior intr-o coada
        for (int i = patientContent.size() - 1; i >= 0; i--) {
            if (patientContent.get(i).contains("queue")) {
                patientContent.remove(i);
            }
        }
        while (nursesContent.size() != 0) {
            nursesContent.remove(0);
        }
        while (doctorContent.size() != 0) {
            doctorContent.remove(0);
        }
    }
    // reseteaza continutul unui pacient cu numele dat
    public final void resetPatientContent(final String name) {
        for (int i = patientContent.size() - 1; i >= 0; i--) {
            if (patientContent.get(i).contains(name + " ")) {
                patientContent.remove(i);
            }
        }
    }
    // gettere si settere
    public final void addPatientContent(final String content) {
        if (!existedContentPatient(content)) {
            this.patientContent.add(content);
        }
    }
    public final void addNursesContent(final String content) {
        this.nursesContent.add(content);
    }
    public final void addDoctorContent(final String content) {
        this.doctorContent.add(content);
    }

    public final ArrayList<String> getPatientContent() {
        return this.patientContent;
    }
    public final ArrayList<String> getNursesContent() {
        return this.nursesContent;
    }
    public final ArrayList<String> getDoctorContent() {
        return this.doctorContent;
    }
    public final boolean existedContentPatient(final String content) {
        for (int i = 0; i < patientContent.size(); i++) {
            if (patientContent.get(i).equals(content)) {
                return true;
            }
        }
        return false;
    }
}
