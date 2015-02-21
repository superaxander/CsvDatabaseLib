package net.csvdatalib.internal;

import net.csvdatalib.internal.exceptions.*;

import java.io.*;

/**
 * Class used to help with file operations done by the database
 *
 * @see net.csvdatalib.Database
 * @author Alexander Stekelenburg
 */
public class FileOperationHelper
{
    /**
     * Get a file and check permissions.(Optionally check if the file exists)
     * @param path the path of the specified file
     * @param mustexist if true will check if the file exists before it is returned
     * @param requiredpermissions the permissions the application needs to work with the file
     * @return the generated File object
     * @throws DatabaseIOException throws exception when file IO failed
     */
    public static File getFile(String path, Boolean mustexist, EnumFilePermissions requiredpermissions) throws DatabaseIOException{
        File file = new File(path);
        Boolean permissionscorrect = false;
        permissionscorrect = requiredpermissions.checkFilePermissions(file);
        if((!mustexist || file.exists()) && file.isDirectory()){
            if(permissionscorrect){
                return file;
            }else{
                throw new DatabaseIOException("Application doesn't have permission");
            }
        }else if(mustexist){
            throw new DatabaseIOException("File doesn't exist");
        }else{
            return file;
        }
    }

    /**
     * Write lines to file(overwriting the existing file, adding a new line at the of the file)
     * @param file the file created with getFile
     * @param strings the lines that will be written to the file
     * @throws DatabaseIOException throws exception when file IO failed
     */
    public static void writeToFile(File file, String[] strings) throws DatabaseIOException{
        try{
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            for(String str : strings){
                fileWriter.append(str).append("\n");
            }
            fileWriter.flush();
            fileWriter.close();
        }catch (IOException e){
            throw new DatabaseIOException("FileIOFailed");
        }
    }

    /**
     * Append lines to file(adding a new line at the end of the file)
     * @param file the file created with getFile
     * @param strings the lines that will be appended to the file
     * @throws DatabaseIOException throws exception when file IO failed
     */
    public static void appendFile(File file, String[] strings) throws DatabaseIOException{
        try{
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true));
            for(String str : strings){
                fileWriter.append(str).append("\n");
            }
            fileWriter.flush();
            fileWriter.close();

        }catch(IOException e){
            throw new DatabaseIOException("File IO Failed");
        }
    }

    /**
     * Load the specified file's lines
     * @param file the file to load
     * @return the lines that were found in the file
     * @throws DatabaseIOException throws a DatabaseIOException when the file couldn't be read
     */
    public static String[] loadFile(File file) throws DatabaseIOException{
        try{
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            return (String[])fileReader.lines().toArray();
        }catch (IOException e){
            throw new DatabaseIOException("Couldn't read file");
        }
    }
}
