package net.csvdatalib.internal.exceptions;

/**
 * Standard exception the csvdatabase application throws
 *
 * @see net.csvdatalib.Database
 * @author Alexander Stekelenburg
 */
public class DatabaseException extends Exception{
    /**
     * Creates a new DatabaseException with a prefix and a generic message
     */
    public DatabaseException(){
        super("CSVDATALIB: Database Exception Occured");
    }

    /**
     * Creates a new DatabaseException with a prefix and a specified message
     * @param message the message to be used when the exception is thrown
     */
    public DatabaseException(String message){
        super("CSVDATALIB:" + message);
    }

    /**
     * Creates a new DatabaseException with a prefix, a specified message and a specified cause
     * @param message the message to be used when the exception is thrown
     * @param cause the cause to be attached to the exception when it is thrown
     */
    public DatabaseException(String message, Throwable cause){
        super("CSVDATALIB: Database Exception Occured", cause);
    }
}
