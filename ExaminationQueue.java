/*
 * Copyright 2019 <Ciupitu Dennis-Mircea 323CA>
 */
package queue;

import java.util.Observable;
import java.util.Observer;

import emergency.InvestigationResult;

import entities.Doctor;

public class ExaminationQueue implements Observer {
    @Override
    public final void update(final Observable o, final Object arg) {
        Examination examination = (Examination) o;
        if (examination.getPatients().get(0).getState().getValue().
                equals(InvestigationResult.TRANSFERED.getValue())) {
            examination.getPatients().remove(0);
            return;
        }
        Doctor doctor = new Doctor();
        doctor.clone(examination.getPatients().get(0).getDoctors().get(0));
        for (int i = 1; i < examination.getPatients().size(); i++) {
            for (int j = 0; j < examination.getPatients().get(i).getDoctors().size() - 1; j++) {
                if (examination.getPatients().get(i).getDoctors().get(j).compare(doctor)) {
                    examination.getPatients().get(i).
                        addDoctor(examination.getPatients().get(i).getDoctors().get(j));
                    examination.getPatients().get(i).getDoctors().remove(j);
                    break;
                }
            }
        }
        examination.getPatients().remove(0);

    }
}
