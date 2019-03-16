package inputRead;
/*
 * Copyright 2019 <Ciupitu Dennis-Mircea 323CA>
 */
import java.util.ArrayList;

public class Room {
    private int simulationLenght;
    private int nurses;
    private int investigators;
    private ArrayList<DoctorRead> doctors;
    private ArrayList<PatientRead> incomingPatients;

    public Room() {
        this.doctors = new ArrayList<DoctorRead>();
        this.incomingPatients = new ArrayList<PatientRead>();
    }
    public final void setSimulationLength(final int a) {
        this.simulationLenght = a;
    }
    public final int getSimulationLength() {
        return this.simulationLenght;
    }
    public final void setNurses(final int nurses) {
        this.nurses = nurses;
    }
    public final int getNurses() {
        return this.nurses;
    }
    public final void setInvestigators(final int investigators) {
        this.investigators = investigators;
    }
    public final int getInvestigators() {
        return this.investigators;
    }
    public final void setDoctors(final ArrayList<DoctorRead> doctors) {
        for (int i = 0; i < doctors.size(); i++) {
            this.doctors.add(doctors.get(i));
        }
    }
    public final ArrayList<DoctorRead> getDoctors() {
        return this.doctors;
    }
    public final void setIncomingPatients(final ArrayList<PatientRead> incomingPatients) {
        for (int i = 0; i < incomingPatients.size(); i++) {
            this.incomingPatients.add(incomingPatients.get(i));
        }
    }
    public final ArrayList<PatientRead> getIncomingPatients() {
        return this.incomingPatients;
    }
}
