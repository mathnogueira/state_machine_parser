package statemachine.models;

import java.util.List;
import java.util.stream.Collectors;

public class StateMachine {

    private List<State> states;
    private List<Transition> transitions;
    private State initialState;

    public void setStates(List<State> states) {
        this.states = states;

        initialState = states
                .stream()
                .filter(state -> state.isInitial())
                .findFirst()
                .get();
    }

    public void setTransitions(List<Transition> transitions) {
        for (State state : states) {
            List<Transition> stateTransitions = transitions
                    .stream()
                    .filter(transition -> transition.getFromState() == state)
                    .collect(Collectors.toList());

            state.setTransitions(stateTransitions);
        }

        this.transitions = transitions;
    }
}
