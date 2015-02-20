package net.csvdatalib.internal;


/**
 * Object holding the database after it is loaded from a file. Used to read/write to the database
 *
 * @see net.csvdatalib.internal.FileOperationHelper
 * @author Alexander Stekelenburg
 */
public class DatabaseFile{
    String[] names;
    String[][] rows;

    /**
     * Create a DatabaseFile object containing the contents of a loaded file
     * @param lines the lines from the loaded file that have to be loaded into the DatabaseFileObject
     */
    public DatabaseFile(String[] lines){
        this.names = lines[0].split("([,])");
        for(int i=1; i < lines.length; i++){
            String[] b = lines[i].split("([,])");
            for(int c=0; c < names.length; c++){
                rows[i-1][c] = b[c];
            }
        }
    }
}
