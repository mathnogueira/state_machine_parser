package statemachine.parsing.builders;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import statemachine.models.State;
import statemachine.parsing.Definition;
import statemachine.utils.exceptions.StateMachineException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StateBuilder {

    private static final String STATES_DEFINITION_REGEX = "\\{([a-zA-Z]+[0-9]+,)*([a-zA-Z]+[0-9]+)+}";

    public static List<State> buildStatesFromDefinition(Definition definition) throws StateMachineException {
        validateDefinition(definition);
        List<State> states = new ArrayList<>();
        String statesSeparatedByComma = definition.getDefinition().replaceAll("\\{", "").replaceAll("}", "");
        String[] stateNames = statesSeparatedByComma.split(",");
        for (String stateName : stateNames) {
            states.add(new State(stateName));
        }
        validateDefinition(definition);
        return states;
    }

    public static void setInitialStateByDefinition(Definition definition, List<State> states)
            throws StateMachineException {

        State initialState = findStateByName(definition.getDefinition(), states);
        initialState.setAsInitial();
    }

    public static void setFinalStatesByDefinition(Definition definition, List<State> states)
            throws StateMachineException {

        String[] finalStatesNames = definition
                .getDefinition()
                .replaceAll("\\{", "")
                .replaceAll("}", "")
                .split(",");

        for (String stateName : finalStatesNames) {
            State state = findStateByName(stateName, states);
            state.setAsFinal();
        }
    }

    public static State findStateByName(String name, List<State> states) throws StateMachineException {
        try {
            return states
                    .stream()
                    .filter(state -> state.getName().equals(name))
                    .findFirst()
                    .get();
        } catch (Exception ex) {
            throw new StateMachineException("Estado " + name + " não existe na definição da máquina, mas está sendo usado na definição de uma transição.");
        }
    }

    private static void validateDefinition(Definition definition) throws StateMachineException {
        String statesDefinition = definition.getDefinition();
        if (!statesDefinition.matches(STATES_DEFINITION_REGEX)) {
            throw new StateMachineException("A definição dos estados está incorreta");
        }
    }
}
