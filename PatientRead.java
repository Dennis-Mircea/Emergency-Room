package inputRead;
/*
 * Copyright 2019 <Ciupitu Dennis-Mircea 323CA>
 */
public class PatientRead {
    private int id;
    private String name;
    private int age;
    private int time;
    private State state;

    public final void setId(final int id) {
        this.id = id;
    }
    public final int getId() {
        return this.id;
    }
    public final void setName(final String name) {
        this.name = name;
    }
    public final String getName() {
        return this.name;
    }
    public final void setAge(final int age) {
        this.age = age;
    }
    public final int getAge() {
        return this.age;
    }
    public final void setTime(final int time) {
        this.time = time;
    }
    public final int getTime() {
        return this.time;
    }
    public final void setState(final State state) {
        this.state = new State();
        this.state.setIllnessName(state.getIllnessName());
        this.state.setSeverity(state.getSeverity());
    }
    public final State getState() {
        return this.state;
    }
}
