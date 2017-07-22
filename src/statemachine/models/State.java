package statemachine.models;

import java.util.List;

public class State {

    private final String name;
    private boolean isFinal;
    private boolean initial;
    private List<Transition> transitions;

    public State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAsInitial() {
        initial = true;
    }

    public void setAsFinal() {
        isFinal = true;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public boolean isInitial() {
        return initial;
    }
}
