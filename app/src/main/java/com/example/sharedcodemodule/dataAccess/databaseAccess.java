package com.example.sharedcodemodule.dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database access functions
 */

public class databaseAccess {

    private Connection connection;
    private boolean openConnection;
    /**
     * Open connection
     * @return connection object
     * @throws DatabaseException
     */

        public Connection openConnection() throws DatabaseException {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Error using classname");
        }
        try {
            String dbName = "familyMapSQLDatabase.db";
            String connectionURL = "jdbc:sqlite:" + dbName;
            this.connection = DriverManager.getConnection(connectionURL);
            this.connection.setAutoCommit(false);

        } catch (SQLException ex) {
            throw new DatabaseException("Unable to open connection to database");
        }
        this.openConnection = true;
        return this.connection;
    }

    /**
     * returns connection object for database access object
     * @return connection object
     * @throws DatabaseException
     */

    public Connection getConnection() throws  DatabaseException {
            if(connection == null){
                return openConnection();
            } else {
                return connection;
            }
    }

    /**
     * close connection
     * @param commit commit changes to database?
     * @throws DatabaseException
     */

    public void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (commit) {
                connection.commit();
            } else {
                //connection.rollback();
            }
            connection.close();
            openConnection = false;
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("close connection failed");
        }
    }

    /**
     * fill tables for testing purposes
     * @throws DatabaseException
     */

    public void fillTables() throws DatabaseException {}

    /**
     * clear database of all table data
     * @throws DatabaseException
     */

//    public void clearTables() throws DatabaseException {
//        String tableName = "persons";
//        String sql = "DELETE FROM "+tableName+"";
//            try (PreparedStatement stmt = connection.prepareStatement(sql)){
//
//                //stmt.setString(1, tableName);
//                stmt.executeUpdate();
//            } catch (SQLException e) {
//                throw new DatabaseException("Error in clearing "+tableName+" table" );
//            }
//    }

    public boolean isOpen(){
        return this.openConnection;
    }

}
