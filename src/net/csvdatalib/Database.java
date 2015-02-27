package net.csvdatalib;

import net.csvdatalib.internal.*;
import net.csvdatalib.internal.exceptions.*;

import java.io.*;

/**
 * Main database file used by applications to use their own database.
 * @see net.csvdatalib.internal.FileOperationHelper
 */
public class Database
{
    public String path;
    public File file;
    public DatabaseFile databaseFile;

    /**
     * Create a new Database object and load in file information(will create a database.csv file in the working directory)
     * @throws DatabaseException throws exception when anything goes wrong
     */
    public Database() throws DatabaseException{
        this(System.getProperty("user.dir").replace('\\', '/') + "/database.csv");
    }

    /**
     * Create a new Database
     * @param path path to load/generate the Database from/at.
     * @throws DatabaseException throws exception when anything goes wrong
     */
    public Database(String path) throws DatabaseException{
        FileOperationHelper.getFile(path, false);
        if(file.exists()){
            databaseFile = new DatabaseFile(FileOperationHelper.loadFile(file));
        }else{
            databaseFile = new DatabaseFile(new String[]{"collum1,collum2", "row1col1, row1col2"});
        }
        this.path = path;
        //this.path = file.getAbsolutePath();
    }
}
