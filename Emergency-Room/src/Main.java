/*
 * Copyright 2019 <Ciupitu Dennis-Mircea 323CA>
 */


import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

import emergency.EmergencyRoom;
import emergency.IllnessType;
import emergency.InvestigationResult;

import inputRead.Room;

import queue.ExaminationQueue;
import queue.Triage;
import queue.Examination;
import queue.Investigations;
import queue.Hospital;

import entities.Doctor;
import entities.DoctorType;
import entities.Patient;

import outputWrite.PrintRound;

public final class Main {
    private Main() {

    }

    public static void main(final String[] args) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            // citirea outputului
            Room roomRead = mapper.readValue(new File(args[0]), Room.class);

            EmergencyRoom room = EmergencyRoom.getInstance();

            // cloneaza camera de urgente
            int simulationLength = roomRead.getSimulationLength();
            room.setNurses(roomRead.getNurses());
            room.setInvestigators(roomRead.getInvestigators());

            // adauga doctorii in noua clasa EmergencyRoom
            for (int i = 0; i < roomRead.getDoctors().size(); i++) {
                Doctor doctor = new Doctor();

                doctor.setId(i);
                doctor.setDoctorType(DoctorType.
                        valueOf(roomRead.getDoctors().get(i).getType()));

                doctor.setIllness();
                doctor.setIsSurgeon(roomRead.getDoctors().get(i).getIsSurgeon());
                doctor.setMaxForTreatment(roomRead.getDoctors().get(i).getMaxForTreatment());

                room.addDoctor(doctor);
            }

            // adauga pacientii in noua clasa
            for (int i = 0; i < roomRead.getIncomingPatients().size(); i++) {
                Patient patient = new Patient();

                patient.setAge(roomRead.getIncomingPatients().get(i).getAge());
                patient.setId(roomRead.getIncomingPatients().get(i).getId());
                patient.setName(roomRead.getIncomingPatients().get(i).getName());
                patient.setTime(roomRead.getIncomingPatients().get(i).getTime());

                patient.setIllnessType(IllnessType.
                        valueOf(roomRead.getIncomingPatients().get(i).getState().getIllnessName()));
                patient.setSeverity(roomRead.getIncomingPatients().get(i).getState().getSeverity());

                patient.setUrgency();

                room.addPatient(patient);
            }

            // seteaza toti doctorii pentru toti pacientii
            room.setDoctorsForPatients();

            // inceperea simularii
            // instantiem triajul si stabilim numarul de asistente
            Triage triageQueue = new Triage();
            triageQueue.setNurses((int) room.getNurses());

            // instantiem coada de examinare
            Examination examinationQueue = new Examination();
            examinationQueue.addObserver(new ExaminationQueue());

            // instantiem coada de investigatii
            Investigations investigationQueue =
                    new Investigations((int) room.getInvestigators());

            // instatiem clasa ce o sa ne ofere outputul si o adaugam ca observer
            PrintRound printRound = new PrintRound();
            room.addObserver(printRound);

            // instantiem spitalul si setam toti doctorii
            Hospital hospital = new Hospital();
            hospital.setDoctors(room.getDoctors());

            long round = 1;
            while (round <= simulationLength) {

                // scoate din spital pacientul care si-a terminat tratamentul
                hospital.getOutPatient(printRound);

                // adauga pacientii cu time = 0 in triage queue
                triageQueue.setPatients(room.getPatients(), (int) round);

                // sortam in functie de severitate si stabilim urgenta
                triageQueue.execute();

                // adaugam pacientii in examinations queue in functie de
                // numarul de asistente si de numarul de pacienti din triage queue
                examinationQueue.setPatients(triageQueue);

                while (examinationQueue.getPatients().size() != 0) {

                    // ia primul pacient din coada si il atribuie primului doctor, verificandu-l
                    examinationQueue.execute(investigationQueue, printRound, hospital);

                    if (!examinationQueue.getPatients().get(0).getState().getValue().
                            equals(InvestigationResult.TRANSFERED.getValue())) {
                        Doctor newDoctor = examinationQueue.
                                getPatients().get(0).getDoctors().get(0);
                        // pune doctorul la finalul cozilor tuturor pacientilor
                        room.getBackDoctor(newDoctor);
                        investigationQueue.getBackDoctor(newDoctor);
                        triageQueue.getBackDoctor(newDoctor);
                    }

                    // elimina pacientul care a fost verificat si pune doctorul la finalul cozilor
                    // pacientul din examinations queue
                    examinationQueue.update();

                }

                // sortam in functie de severitate si urgenta
                investigationQueue.sort();

                // verifica pacientii in functie de numarul de investigatori
                investigationQueue.setResult();

                // adauga pacientii verificati inapoi in examinations queue
                investigationQueue.execute(examinationQueue);

                // printeaza pacientii ramasi in investigations queue
                investigationQueue.printExistedPatient(printRound);

                // printeaza pacientii rmasi in examinations queue
                examinationQueue.printExistedPatients(printRound);

                // printeaza pacientii ramasi in triage queue
                triageQueue.printExistedPatients(printRound);

                // sortam alfabetic pacientii spitalizati
                hospital.sort();

                // printam asistentele ce se vor ocupa de pacientii spitalizati
                hospital.printNurses(printRound, (int) room.getNurses());

                // reimprospatam listele de pacienti ale doctorilor
                hospital.refresh();

                // printam doctorii care se ocupa de pacientii lor spitalizati
                hospital.goHome(printRound);

                // dam update camerei de urgenta
                room.update();

                // trecem la runda urmatoare
                round++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
