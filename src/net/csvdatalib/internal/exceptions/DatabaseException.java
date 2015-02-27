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
        this("Database Exception Occured");
    }

    /**
     * Creates a new DatabaseException with a prefix and a specified message
     * @param message the message to be used when the exception is thrown
     */
    public DatabaseException(String message){
        super("CSVDATALIB:" + message);
    }
}
