package com.example.sharedcodemodule.dataAccess;

import com.example.sharedcodemodule.model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * database interactions for user
 * @param <T> generic variable
 */

public class userDao<T> {

    private final Connection connection;

    public userDao(Connection connection) {
        this.connection = connection;
    }


    /**
     * add user to table
     * @param user user object to insert
     */

    public void insert(user user) throws DatabaseException{
        String sql = "INSERT INTO users (userID, userName, password, email, firstName, lastName, gender) VALUES (?,?,?,?,?,?,?) ";
        //System.out.println(sql + user.getID());
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, user.getID());
            stmt.setString(2, user.getUserName());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getFirstName());
            stmt.setString(6, user.getLastName());
            stmt.setString(7, user.getGender());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error occured while inserting user into database: " + e);
        }
    }

    public void addUsers(List<user> users) throws DatabaseException{
        user user;
        for(int i = 0; i < users.size(); i++ ){
            user = users.get(i);
            if(user.getID() == null){
                user.setID(String.valueOf(UUID.randomUUID()));
            }
            String sql = "INSERT INTO users (userID, userName, password, email, firstName, lastName, gender) VALUES (?,?,?,?,?,?,?) ";
            //System.out.println(sql + user.getID());
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, user.getID());
                stmt.setString(2, user.getUserName());
                stmt.setString(3, user.getPassword());
                stmt.setString(4, user.getEmail());
                stmt.setString(5, user.getFirstName());
                stmt.setString(6, user.getLastName());
                stmt.setString(7, user.getGender());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException("Error occured while inserting user into database: " + e);
            }
        }
    }

    public user findByName(String userName){
        user outUser;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE userName = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if(rs.next()) {
                outUser = new user(rs.getString("userID"), rs.getString("userName"), rs.getString("password"), rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"), rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouceID"));
               // rs.close();
                return outUser;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * find user in database and return model user object
     * @param userID id of user to find
     * @return model user object
     */

    public user find(String userID){
        user outUser;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE userID = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, userID);
            rs = stmt.executeQuery();
            if(rs.next()) {
                outUser = new user(rs.getString("userID"), rs.getString("userName"), rs.getString("password"), rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"), rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouceID"));
                return outUser;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public List<user> findUsers() throws DatabaseException {
        List<user> returnList = null;
        String sql = "SELECT * FROM users";
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            //stmt.setString(1, associatedUserName);
            rs = stmt.executeQuery();
            returnList = new ArrayList<user>();
            while(rs.next()){
                returnList.add(  new user(rs.getString("userID"), rs.getString("userName"), rs.getString("password"), rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"), rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouceID")));
            }
            return returnList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error finding person");
        } finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * update column of specific user
     * @param userID id of user to change
     * @param changeParameter column to change
     * @param input value to change, variable is generic so it will accept strings and integers
     */

    public void updateUser(String userID, String changeParameter, T input){}

    /**
     * get specific user from table
     * @param userID id of user to get
     * @return user object
     */

    public user getUserName(String userID){
        return null;
    }

    /**
     * delete specific user from table
     * @param userID id of user to delete
     */

    public void deleteUser(String userID){}

    /**
     * clear table of all users
     */

    public void clear() throws DatabaseException {
        String sql = "DELETE FROM users";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error occurred while clearing person database: " + e);
        }
    }

}
