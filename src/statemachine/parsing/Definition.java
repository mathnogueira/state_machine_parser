package statemachine.parsing;

public class Definition {

    private final String definition;
    private final EDefinitionType type;

    public Definition(String definition, EDefinitionType type) {
        this.definition = removeLastComma(definition);
        this.type = type;
    }

    private String removeLastComma(String definition) {
        String newDefinition = definition;
        int lastCharIndex = definition.length() - 1;
        if (definition.charAt(lastCharIndex) == ',') {
            newDefinition = definition.substring(0, lastCharIndex   );
        }
        return newDefinition;
    }

    public String getDefinition() {
        return definition;
    }

    public EDefinitionType getType() {
        return type;
    }
}
