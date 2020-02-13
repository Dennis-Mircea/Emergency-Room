package inputRead;
/*
 * Copyright 2019 <Ciupitu Dennis-Mircea 323CA>
 */
public class DoctorRead {
    private String type;
    private boolean isSurgeon;
    private int maxForTreatment;

    public final void setType(final String type) {
        this.type = type;
    }
    public final String getType() {
        return this.type;
    }
    public final void setIsSurgeon(final boolean isSurgeon) {
        this.isSurgeon = isSurgeon;
    }
    public final boolean getIsSurgeon() {
        return this.isSurgeon;
    }
    public final void setMaxForTreatment(final int maxForTreatment) {
        this.maxForTreatment = maxForTreatment;
    }
    public final int getMaxForTreatment() {
        return this.maxForTreatment;
    }
}
