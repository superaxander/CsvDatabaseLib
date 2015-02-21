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
    private String path;
    public File file;

    /**
     * Create a new Database object and load in file information(will create a database.csv file in the working directory)
     * @throws DatabaseException throws exception when anything goes wrong
     */
    public Database() throws DatabaseException{
        String path = System.getProperty("user.dir").replace('\\', '/') + "/database.csv";
        FileOperationHelper.getFile(path, false, EnumFilePermissions.READ_WRITE);
        this.path = path;
    }

    /**
     * Create a new Database
     * @param path path to load/generate the Database from/at.
     * @throws DatabaseException throws exception when anything goes wrong
     */
    public Database(String path) throws DatabaseException{
        this.file = FileOperationHelper.getFile(path, false, EnumFilePermissions.READ_WRITE);
        this.path = file.getAbsolutePath();
    }
}
