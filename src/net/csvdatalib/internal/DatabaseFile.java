package net.csvdatalib.internal;


import net.csvdatalib.internal.exceptions.*;

import java.util.*;
import java.util.stream.*;

/**
 * Object holding the database after it is loaded from a file. Used to read/write to the database
 *
 * @see net.csvdatalib.internal.FileOperationHelper
 * @author Alexander Stekelenburg
 */
public class DatabaseFile{
    public String[] collums;
    public String[][] rows;

    /**
     * Create a DatabaseFile object containing the contents of a loaded file
     * @param lines the lines from the loaded file that have to be loaded into the DatabaseFileObject
     */
    public DatabaseFile(String[] lines){
        this.collums = lines[0].split("([,])");
        for(int i=1; i < lines.length; i++){
            String[] b = lines[i].split("([,])");
            for(int c=0; c < collums.length; c++){
                rows[i - 1][c] = b[c];
            }
        }
    }

    /**
     * Finds specified collum in database
     * @param collum the collum to be searched for
     * @return the number of the collum(returns -1 when the collum wasn't found)
     * @throws DatabaseException throws a DatabaseException when there were multiple collums with the same name
     */
    private int findCollum(String collum) throws DatabaseException{
        int collumNumber = -1;
        boolean cnSet = false;
        for(int d=0; d < collums.length; d++){
            if(collums[d].equals(collum)){
                if(cnSet){
                    throw new DatabaseException("Duplicate collums");
                }else{
                    collumNumber = d;
                    cnSet = true;
                }
            }
        }
        return collumNumber;
    }

    /**
     * Finds the value of a collum in a row
     * @param collum the collum to search for
     * @param row the row to search for
     * @return the value of the collum in the row
     * @throws DatabaseException throws a DatabaseException when the specified collum couldn't be found
     */
    private String findCollumInRow(String collum, int row) throws DatabaseException{
        int collumNumber = findCollum(collum);
        if(collumNumber < 0){
            return this.rows[collumNumber][row];
        }else{
            throw new DatabaseException("Specified Collum wasn't found");
        }
    }

    /**
     * Gets the string in the specified row
     * @param collum the collum to get the value from
     * @param row the row to get the value from
     * @return the value of the collum in the row
     * @throws DatabaseException throws a DatabaseException when findCollumInRow throws a DatabaseException
     */
    public String getString(String collum, int row) throws DatabaseException{
        return findCollumInRow(collum, row);
    }

    public void setString(String value, String collum, int row) throws DatabaseException{
        rows[row][findCollum(collum)] = value;
    }

    public void setString(String value, String collum) throws DatabaseException{
        rows[rows.length][findCollum(collum)] = value;
    }
}
