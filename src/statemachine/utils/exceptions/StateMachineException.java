package statemachine.utils.exceptions;

public class StateMachineException extends Exception {

    public StateMachineException(String message) {
        super(message);
    }

    public StateMachineException(String message, Throwable cause) {
        super(message, cause);
    }
}
