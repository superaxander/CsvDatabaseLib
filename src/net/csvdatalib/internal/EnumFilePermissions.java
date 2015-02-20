package net.csvdatalib.internal;

import java.io.*;

/**
 * An enumeration used to check different file permissions
 *
 * @see net.csvdatalib.internal.FileOperationHelper
 * @author Alexander Stekelenburg
 */
public enum EnumFilePermissions
{
    NONE, READ, WRITE, EXECUTE, READ_WRITE, READ_EXECUTE, WRITE_EXECUTE;

    /**
     * Check if the file has the correct permissions
     * @param file the file that has to be checked
     * @return if the permissions are correct
     */
    public boolean checkFilePermissions(File file){
        boolean permissionscorrect = false;
        switch(this){
            case WRITE: permissionscorrect = file.canWrite();
                break;
            case EXECUTE: permissionscorrect = file.canExecute();
                break;
            case READ_WRITE: permissionscorrect = file.canRead() && file.canWrite();
                break;
            case READ_EXECUTE: permissionscorrect = file.canRead() && file.canExecute();
                break;
            case WRITE_EXECUTE: permissionscorrect = file.canWrite() && file.canExecute();
                break;
            case NONE: permissionscorrect = true;
                break;
            case READ: permissionscorrect = file.canRead();
                break;
        }
        return permissionscorrect;
    }
}
