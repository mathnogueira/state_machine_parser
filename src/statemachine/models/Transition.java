package statemachine.models;

public class Transition {

    private final State from;
    private final State to;
    private final String trigger;

    public Transition(State from, State to, String trigger) {
        this.from = from;
        this.to = to;
        this.trigger = trigger;
    }

    public String getTrigger() {
        return trigger;
    }

    public State getFromState() {
        return from;
    }
}
