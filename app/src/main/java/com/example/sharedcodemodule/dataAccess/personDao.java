package com.example.sharedcodemodule.dataAccess;

import com.example.sharedcodemodule.model.person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * database calls for person
 * @param <T> generic variable
 */

public class personDao<T> {

    private final Connection connection;

    public personDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * add person to database
     * @param person person object to add into database
     */

    public void insert(person person) throws DatabaseException {
        String sql = "INSERT INTO persons (personID, associatedUserName, firstName, lastName, gender, fatherID, motherID, spouceID) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUserName());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouceID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error inserting person into database");
        }
    }

    public void addPersons(List<person> personList) throws DatabaseException{

        for(int i = 0; i < personList.size(); i++) {
            person person = personList.get(i);
            String sql = "INSERT INTO persons (personID, associatedUserName, firstName, lastName, gender, fatherID, motherID, spouceID) VALUES (?,?,?,?,?,?,?,?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setString(1, person.getPersonID());
                stmt.setString(2, person.getAssociatedUserName());
                stmt.setString(3, person.getFirstName());
                stmt.setString(4, person.getLastName());
                stmt.setString(5, person.getGender());
                stmt.setString(6, person.getFatherID());
                stmt.setString(7, person.getMotherID());
                stmt.setString(8, person.getSpouceID());
                stmt.executeUpdate();
            } catch (SQLException e) {
                    throw new DatabaseException("Error inserting person into database" + e);
            }
        }
    }

    /**
     * get person from database
     * @return returns object version of person
     */

    public person find(String personID) throws DatabaseException{

        person outPerson = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM persons WHERE personID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if(rs.next()){
                outPerson = new person(rs.getString("personID"),rs.getString("associatedUserName"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("gender"),rs.getString("fatherID"),rs.getString("motherID"),rs.getString("spouceID"));
                return outPerson;
            }
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
        return null;
    }


    /**
     * get all persons associated with user
     * @param associatedUserName username for user
     * @return list of person objects
     */

    public List<person> findPersons(String associatedUserName) throws DatabaseException {
        List<person> returnList = null;
        String sql = "SELECT * FROM persons WHERE associatedUserName = ?";
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, associatedUserName);
            rs = stmt.executeQuery();
            returnList = new ArrayList<person>();
            while(rs.next()){
                returnList.add( new person(rs.getString("personID"),rs.getString("associatedUserName"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("gender"),rs.getString("fatherID"),rs.getString("motherID"),rs.getString("spouceID")));
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
     * get all persons
     * @return list of person objects
     */

    public List<person> findPersons() throws DatabaseException {
        List<person> returnList = null;
        String sql = "SELECT * FROM persons";
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            //stmt.setString(1, associatedUserName);
            rs = stmt.executeQuery();
            returnList = new ArrayList<person>();
            while(rs.next()){
                returnList.add( new person(rs.getString("personID"),rs.getString("associatedUserName"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("gender"),rs.getString("fatherID"),rs.getString("motherID"),rs.getString("spouceID")));
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
     * edit person in database
     * @param id id of person to edit
     * @param field column to edit
     * @param input input new entry, uses generic variable so it can accept strings and integers
     */
    public void editPerson(String id, String field, T input){ }

    /**
     * delete person from database
     * @param id id of person to delete
     */

    public void deletePerson(String id){}

    /**
     * clear all persons associated with a user
     * @param associatedUserName id of user to delete
     */

    public void clear(String associatedUserName) throws DatabaseException{
        String sql = "DELETE FROM persons WHERE associatedUserName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, associatedUserName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error occurred while clearing person database: " + e);
        }
    }

    /**
     * clear database of all persons
     */

    public void clear() throws DatabaseException {
        String sql = "DELETE FROM persons";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error occurred while clearing person database: " + e);
        }
    }
}
