import statemachine.models.StateMachine;
import statemachine.parsing.builders.StateMachineBuilder;
import statemachine.utils.exceptions.StateMachineException;

import java.io.File;

public class Main {

    public static void main(String args[]) {
        StateMachineBuilder builder = new StateMachineBuilder();
        File definition = new File("definitions/machine_01.txt");

        try {
            StateMachine machine = builder.createFromFile(definition);
            System.out.println(machine);
        } catch (StateMachineException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
