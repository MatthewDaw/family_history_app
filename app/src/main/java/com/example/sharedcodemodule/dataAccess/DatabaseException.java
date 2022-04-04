package com.example.sharedcodemodule.dataAccess;

/**
 * Database exception object
 */

public class DatabaseException extends Exception {

    /**
     * constructor for exception with only simple string
     * @param s error string
     */

    public DatabaseException(String s) {
        super(s);
    }

    /**
     * constructor for exception wtih error string and throwable error
     * @param s error string
     * @param throwable throwable error
     */

    public DatabaseException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
