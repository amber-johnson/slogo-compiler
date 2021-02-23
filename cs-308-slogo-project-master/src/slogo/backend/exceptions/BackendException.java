package slogo.backend.exceptions;

/**
 * Creates a new type of exception that occurs in any of the classes in the slogo.backend/ package.
 * Assumes the user does not need to know from where the exception was thrown.
 * Used in BackendManager.java, CommandBlockManager.java, CommandTree.java, and UserDefined.java
 *
 * Ex:  throw new BackendException();
 *
 * @author  Eric Han, Amber Johnson, Ha Nguyen
 */

public class BackendException extends Exception{
    private final Throwable ex = new Exception();
    private final String message;

    public BackendException(String message) {
        this.message = message;
    }

    public BackendException(Throwable ex, String message) {
        this.message = message;
    }

    /**
     * Gets the error message to display to the user for a BackendException object
     *
     * @return an error message as a String
     */
    public String getMessage() {
        return message;
    }
}
