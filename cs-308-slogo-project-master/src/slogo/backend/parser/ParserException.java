package slogo.backend.parser;

/**
 * Creates a new type of exception that occurs when the user input is not valid.
 * Assumes the user does not need to know from where the exception was thrown.
 * Used in Parser.java
 * Ex: throw new ParserException(single_cmd);
 *
 * @author Amber Johnson and Eric Han
 */
public class ParserException extends RuntimeException {

    private static final String ERROR_MESSAGE = "The following command is not recognized: ";
    private final String message;

    public ParserException(String command) {
        this.message = ERROR_MESSAGE + command;
    }

    /**
     * Gets the error message to display to the user for a ParserException object
     *
     * @return a String of the error message set in Parser.java
     */
    public String getMessage() {return message;}
}
