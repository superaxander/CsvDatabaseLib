package net.csvdatalib.internal;


import net.csvdatalib.*;
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
            for(int c=0; c < this.collums.length; c++){
                this.rows[i - 1][c] = b[c];
            }
        }
    }

    /**
     * Remove all instances of a duplicate collum
     */
    public void removeDuplicateCollums(){
        for(int i = 0; i < this.collums.length; i++){
            try{
                this.findCollum(this.collums[i]);
            }catch(DatabaseException e){
                if(e.getMessage() == "CSVDATALIB:Duplicate collums"){
                    List<String> list = new ArrayList<String>(Arrays.asList(this.collums));
                    list.removeAll(Arrays.asList(this.collums[i]));
                    this.collums = list.toArray(new String[list.size()]);
                    for(int b = 0; b < this.rows.length; b++){
                        List<String> list2 = new ArrayList<String>(Arrays.asList(this.rows[i]));
                        list2.remove(i);
                        this.rows[i] = list2.toArray(new String[list2.size()]);
                    }
                }
            }
        }
    }
    /**
     * Finds specified collum in database
     * @param collum the collum to be searched for
     * @return the number of the collum(returns -1 when the collum wasn't found)
     * @throws DatabaseException throws a DatabaseException when there were multiple collums with the same name or when the specified collum couldn't be found
     */
    private int findCollum(String collum) throws DatabaseException{
        int collumNumber = -1;
        boolean cnSet = false;
        for(int d=0; d < this.collums.length; d++){
            if(this.collums[d].equals(collum)){
                if(cnSet){
                    throw new DatabaseException("Duplicate collums");
                }else{
                    collumNumber = d;
                    cnSet = true;
                }
            }
        }
        if(collumNumber < 0){
            throw new DatabaseException("Specified collum wasn't found");
        }
        return collumNumber;
    }

    /**
     * Finds the value of a collum in a row
     * @param collum the collum to search for
     * @param row the row to search for
     * @return the value of the collum in the row
     * @throws DatabaseException throws a DatabaseException when findCollum throws a DatabaseException
     */
    private String findCollumInRow(String collum, int row) throws DatabaseException{
        int collumNumber = this.findCollum(collum);
        return this.rows[collumNumber][row];
    }

    /**
     * Gets the list of current collums
     * @return the collums in the current DatabaseFile
     */
    public String[] getCollums(){
        return this.collums;
    }

    /**
     * Adds a collum the database
     * @param collumname the name to call the collum
     * @throws DatabaseException throws a DatabaseException when the collum already existed or when duplicate collums already exist
     */
    public void addCollum(String collumname) throws DatabaseException{
        try{
            this.findCollum(collumname);
            this.collums[this.collums.length] = collumname;
        }catch (DatabaseException e){
            throw new DatabaseException("Collum already exists");
        }
    }

    /**
     * Removes a collum from the database
     * @param collumname the name of the collum that has to be removed
     * @throws DatabaseException throws a DatabaseException when the collum did not exist or when a duplicate collum was found
     */
    public void removeCollum(String collumname) throws DatabaseException{
        int collumnumber = this.findCollum(collumname);
        List<String> list = new ArrayList<String>(Arrays.asList(this.collums));
        list.removeAll(Arrays.asList(collumname));
        this.collums = list.toArray(new String[list.size()]);
        for(int i = 0; i < this.rows.length; i++){
            List<String> list2 = new ArrayList<String>(Arrays.asList(this.rows[i]));
            list2.remove(collumnumber);
            this.rows[i] = list2.toArray(new String[list2.size()]);
        }
    }
    /**
     * Gets the string in the specified row
     * @param collum the collum to get the value from
     * @param row the row to get the value from
     * @return the value of the collum in the row
     * @throws DatabaseException throws a DatabaseException when findCollum throws a DatabaseException
     */
    public String getString(String collum, int row) throws DatabaseException{
        return this.findCollumInRow(collum, row);
    }

    /**
     * Sets the string in the specified collum and row
     * @param value the value to be set
     * @param collum in which collum to put the value
     * @param row in which row to put the value
     * @throws DatabaseException throws a DatabaseException when findCollum throws a DatabaseException
     */
    public void setString(String value, String collum, int row) throws DatabaseException{
        this.rows[row][this.findCollum(collum)] = value;
    }

    /**
     * Sets the string in the specified collum in a new row
     * @param value the value to be set
     * @param collum in which collum to put the value
     * @throws DatabaseException throws a DatabaseException when findCollum throws a DatabaseException
     */
    public void setString(String value, String collum) throws DatabaseException{
        this.setString(value, collum, this.rows.length);
    }

    /**
     * Gets the integer in the specified row
     * @param collum the collum to get the value from
     * @param row the row to get the value from
     * @return the value of the collum in row
     * @throws DatabaseException throws a DatabaseException when the found value wasn't a valid integer or when findCollum throws a DatabaseException
     */
    public int getInteger(String collum, int row) throws DatabaseException{
        try{
            return Integer.valueOf(this.rows[row][this.findCollum(collum)]);
        }catch (NumberFormatException nfe){
            throw new DatabaseException("Value in specified collum and row(Collum: "+collum+" Row: "+String.valueOf(row)+") wasn't a valid integer");
        }
    }

    /**
     * Sets the integer in the specified collum and row
     * @param value the value to be set
     * @param collum in which collum to put the value
     * @param row in which row to put the value
     * @throws DatabaseException throws a DatabaseException when findCollum throws a DatabaseException
     */
    public void setInteger(int value, String collum, int row) throws DatabaseException{
        this.setString(String.valueOf(value), collum, row);
    }

    /**
     * Sets the integer in the specified collum in a new row
     * @param value the value to be set
     * @param collum in which collum to put the value
     * @throws DatabaseException throws a DatabaseException when findCollum throws a DatabaseException
     */
    public void setInteger(int value, String collum) throws DatabaseException{
        this.setInteger(value, collum, this.rows.length);
    }

    /**
     * Gets the boolean in the specified row
     * @param collum the collum to get the value from
     * @param row the row to get the value from
     * @return the value of the collum in row
     * @throws DatabaseException throws a DatabaseException when the found value wasn't a valid boolean or when findCollum throws a DatabaseException
     */
    public boolean getBoolean(String collum, int row) throws DatabaseException{
        try{
            return Boolean.getBoolean(this.rows[row][this.findCollum(collum)]);
        }catch (NumberFormatException nfe){
            throw new DatabaseException("Value in specified collum and row(Collum: "+collum+" Row: "+String.valueOf(row)+") wasn't a valid boolean");
        }
    }

    /**
     * Sets the boolean in the specified collum and row
     * @param value the value to be set
     * @param collum in which collum to put the value
     * @param row in which row to put the value
     * @throws DatabaseException throws a DatabaseException when findCollum throws a DatabaseException
     */
    public void setBoolean(boolean value, String collum, int row) throws DatabaseException{
        this.setString(String.valueOf(value), collum, row);
    }

    /**
     * Sets the boolean in the specified collum in a new row
     * @param value the value to be set
     * @param collum in which collum to put the value
     * @throws DatabaseException throws a DatabaseException when findCollum throws a DatabaseException
     */
    public void setBoolean(boolean value, String collum) throws DatabaseException{
        this.setBoolean(value, collum, this.rows.length);
    }

    /**
     * Gets the rows and collums and turns them into lines(for writing to a file)
     * @return the rows and collums in a string array.
     */
    private String[] getLines(){
        String[] lines = new String[this.rows.length];
        lines[0] = "";
        for(int i = 0; i < this.collums.length; i++){
            if(i != this.collums.length-1){
                lines[0] += this.collums[i] + ",";
            }else{
                lines[0] += this.collums[i];
            }
        }
        for(int b = 0; b < this.rows.length; b++){
            String[] row = rows[b];
            for(int a = 0; a < this.rows.length; a++){
                if(a != this.rows.length-1){
                    lines[b + 1] += row[a] + ",";
                }else{
                    lines[b + 1] += row[a];
                }
            }
        }
        return lines;
    }
    /**
     * Saves the database to file
     * @param database the current database instance
     * @throws DatabaseIOException throws a DatabaseIOException when the file couldn't be saved
     */
    public void save(Database database) throws DatabaseIOException{
        FileOperationHelper.writeToFile(database.file, this.getLines());
    }

    /**
     * Concatenates all the lines and returns them
     * @return the concatenated lines
     */
    public String toString(){
        String concatLines = "";
        for(String line : this.getLines()){
            concatLines += line + "\n";
        }
        return concatLines;
    }
}
