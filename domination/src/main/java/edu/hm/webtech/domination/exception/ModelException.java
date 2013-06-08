package edu.hm.webtech.domination.exception;

/**
 * Exception Klasse für das Model.
 *
 * @author Sebastian Stumpf
 */
public class ModelException extends Exception {

    /**
     * Default Konstruktor.
     */
    public ModelException() {}

    /**
     * Konstruktor mit Message.
     * @param message Die Message.
     */
    public ModelException(String message) {
        super(message);
    }
}
