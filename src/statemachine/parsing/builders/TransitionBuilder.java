package statemachine.parsing.builders;

import statemachine.models.State;
import statemachine.models.Transition;
import statemachine.parsing.Definition;
import statemachine.utils.exceptions.StateMachineException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransitionBuilder {

    private static final String TRANSITION_DEFINITION_REGEX = "\\(([a-zA-Z]+[0-9]+),([a-zA-Z0-9])->([a-zA-Z]+[0-9]+)\\)";

    public static List<Transition> buildTransitionsFromDefinitions(List<Definition> definitions, List<State> states)
            throws StateMachineException {

        List<Transition> transitions = new ArrayList<>();
        for (Definition definition : definitions) {
            transitions.add(buildTransition(definition, states));
        }
        return transitions;
    }

    private static Transition buildTransition(Definition definition, List<State> states) throws StateMachineException {
        validateDefinition(definition);
        String transitionDefinition = definition.getDefinition();
        String[] data = findDataFromDefinition(transitionDefinition);
        State fromState = StateBuilder.findStateByName(data[0], states);
        State toState = StateBuilder.findStateByName(data[2], states);

        Transition transition = new Transition(fromState, toState, data[1]);
        return transition;
    }

    private static void validateDefinition(Definition definition) throws StateMachineException {
        String transitionDefiniton = definition.getDefinition();
        if (!transitionDefiniton.matches(TRANSITION_DEFINITION_REGEX)) {
            throw new StateMachineException("A definição da transição ("+ transitionDefiniton +") está incorreta");
        }
    }

    private static String[] findDataFromDefinition(String definition) {
        String[] data = new String[3];
        Pattern pattern = Pattern.compile(TRANSITION_DEFINITION_REGEX);
        Matcher matcher = pattern.matcher(definition);

        if (matcher.find()) {
            data[0] = matcher.group(1);
            data[1] = matcher.group(2);
            data[2] = matcher.group(3);
        }

        return data;
    }
}
