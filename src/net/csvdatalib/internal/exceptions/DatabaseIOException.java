package net.csvdatalib.internal.exceptions;

/**
 * Exception used by the csvdatabase application when something goes wrong during reading/writing
 *
 * @see net.csvdatalib.internal.FileOperationHelper
 * @author Alexander Stekelenburg
 */
public class DatabaseIOException extends DatabaseException{
    /**
     * Creates a new DatabaseIOException with a specified cause
     * @param cause the cause to be added to the exceptions message when thrown
     */
    public DatabaseIOException(String cause){
        super("Database Input/Output Exception: " + cause);
    }
}
