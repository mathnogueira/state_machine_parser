package statemachine.parsing.builders;

import statemachine.models.State;
import statemachine.models.StateMachine;
import statemachine.models.Transition;
import statemachine.parsing.Definition;
import statemachine.parsing.EDefinitionType;
import statemachine.parsing.Parser;
import statemachine.utils.FileUtils;
import statemachine.utils.exceptions.StateMachineException;
import statemachine.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StateMachineBuilder {

    private List<State> states;
    private List<String> alphabet;

    public StateMachine createFromFile(File machineDefinitionFile) throws StateMachineException {
        try {
            String machineDefinition = FileUtils.getContent(machineDefinitionFile);
            String sanitizedMachineDefinition = StringUtils.sanitize(machineDefinition);
            Parser stateMachineParser = new Parser(sanitizedMachineDefinition);

            List<Definition> definitionList = stateMachineParser.buildDefinitionsFromString();
            return buildMachineFromDefinitions(definitionList);
        } catch (IOException ex) {
            throw new StateMachineException("Arquivo de definição da máquina não existe", ex);
        }
    }

    private StateMachine buildMachineFromDefinitions(List<Definition> definitions) throws StateMachineException {
        StateMachine stateMachine = new StateMachine();
        List<State> states = buildStatesFromDefinitions(definitions);
        alphabet = buildAlphabetFromDefinitions(definitions);
        List<Transition> transitions = buildTransitionsFromDefinitions(definitions);

        stateMachine.setStates(states);
        stateMachine.setTransitions(transitions);
        return stateMachine;
    }

    private List<State> buildStatesFromDefinitions(List<Definition> definitions) throws StateMachineException {
        Definition statesDefinition = definitions
                .stream()
                .filter(definition -> definition.getType() == EDefinitionType.STATES_DEFINITION)
                .findFirst()
                .get();

        Definition initialStateDefinition = definitions
                .stream()
                .filter(definition -> definition.getType() == EDefinitionType.INITIAL_STATE_DEFINITION)
                .findFirst()
                .get();

        Definition finalStateDefinition = definitions
                .stream()
                .filter(definition -> definition.getType() == EDefinitionType.FINAL_STATES_DEFINITION)
                .findFirst()
                .get();

        List<State> states = StateBuilder.buildStatesFromDefinition(statesDefinition);
        StateBuilder.setInitialStateByDefinition(initialStateDefinition, states);
        StateBuilder.setFinalStatesByDefinition(finalStateDefinition, states);
        this.states = states;
        return states;
    }

    private List<Transition> buildTransitionsFromDefinitions(List<Definition> definitions)
            throws StateMachineException {
        List<Definition> transitionDefinitions = definitions
                .stream()
                .filter(definition -> definition.getType() == EDefinitionType.TRANSITION_DEFINITION)
                .collect(Collectors.toList());

        List<Transition> transitions = TransitionBuilder.buildTransitionsFromDefinitions(transitionDefinitions, states);

        for (Transition transition : transitions) {
            if (!alphabet.contains(transition.getTrigger())) {
                throw new StateMachineException("O simbolo " + transition.getTrigger() + " não está definido no alfabeto, mas está sendo usado em uma transição.");
            }
        }

        return transitions;
    }

    private List<String> buildAlphabetFromDefinitions(List<Definition> definitions) {
        Definition alphabetDefinition = definitions
                .stream()
                .filter(definition -> definition.getType() == EDefinitionType.ALPHABET_DEFINITION)
                .findFirst()
                .get();

        String alphabetDefinitionContent = alphabetDefinition.getDefinition();
        String[] symbolsAlphabet = alphabetDefinitionContent
                .replaceAll("\\{", "")
                .replaceAll("}", "")
                .split(",");
        return Arrays.asList(symbolsAlphabet);
    }

}
