package statemachine.parsing;


import statemachine.models.StateMachine;
import statemachine.utils.exceptions.StateMachineException;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final String definition;

    public Parser(String definition) {
        this.definition = definition;
    }

    public List<Definition> buildDefinitionsFromString() throws StateMachineException {
        String[] definitionArguments = definition.split("\\n");
        verifityOveralldefinition(definitionArguments);
        return createDefinitions(definitionArguments);
    }

    private void verifityOveralldefinition(String[] definitionArguments) throws StateMachineException {
        int defLength = definitionArguments.length;
        if (defLength == 0) {
            throw new StateMachineException("O arquivo de definição da máquina está vazio.");
        }

        if (!"(".equals(definitionArguments[0])) {
            throw new StateMachineException("A definição deve estar encapsulada por parentesis. Está faltando o '('");
        }

        if (!definitionArguments[defLength - 1].contains(")")) {
            throw new StateMachineException("A definição deve estar encapsulada por parentesis. Está faltando o ')'");
        }
    }

    private List<Definition> createDefinitions(String[] definitionArguments) throws StateMachineException {
        Definition stateDefinition = new Definition(definitionArguments[1], EDefinitionType.STATES_DEFINITION);
        Definition alphabetDefinition = new Definition(definitionArguments[2], EDefinitionType.ALPHABET_DEFINITION);
        List<Definition> transitionDefitions = createTransitionDefinitions(definitionArguments, 3);
        int endOfTransitions = 5 + transitionDefitions.size();
        Definition initialStateDefiniton = new Definition(definitionArguments[endOfTransitions], EDefinitionType.INITIAL_STATE_DEFINITION);
        Definition finalStatesDefinition = new Definition(definitionArguments[endOfTransitions + 1], EDefinitionType.FINAL_STATES_DEFINITION);

        List<Definition> definitions = new ArrayList<>();
        definitions.add(stateDefinition);
        definitions.add(alphabetDefinition);
        definitions.addAll(transitionDefitions);
        definitions.add(initialStateDefiniton);
        definitions.add(finalStatesDefinition);
        return definitions;
    }

    private List<Definition> createTransitionDefinitions(String[] definitionArguments, int start)
            throws StateMachineException {
        List<Definition> definitions = new ArrayList<>();
        if (!"{".equals(definitionArguments[start])) {
            throw new StateMachineException("As definições das transições devem estar encapsuladas por chaves. Está faltando o '{'");
        }

        String currentLineContent;
        int currentLine = start + 1;
        do {
            currentLineContent = definitionArguments[currentLine];
            if (currentLineContent.matches("}(,?)")) break;
            definitions.add(new Definition(currentLineContent, EDefinitionType.TRANSITION_DEFINITION));
            currentLine++;
        } while (currentLine < definitionArguments.length);

        if (currentLine >= definitionArguments.length) {
            throw new StateMachineException("As definições das transições devem estar encapsuladas por chaves. Está faltando o '}'");
        }

        return definitions;
    }

}
