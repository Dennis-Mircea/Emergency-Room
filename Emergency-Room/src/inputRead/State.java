package inputRead;
/*
 * Copyright 2019 <Ciupitu Dennis-Mircea 323CA>
 */
public class State {
    private String illnessName;
    private int severity;

    public final void setIllnessName(final String illnessName) {
        this.illnessName = illnessName;
    }
    public final String getIllnessName() {
        return this.illnessName;
    }
    public final void setSeverity(final int severity) {
        this.severity = severity;
    }
    public final int getSeverity() {
        return this.severity;
    }
}
