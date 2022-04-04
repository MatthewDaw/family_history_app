package com.example.sharedcodemodule.dataAccess;

import com.example.sharedcodemodule.model.authToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *Object class to generate authtokens for users.
 */


public class authTokenDao {

    private final Connection connection;

    public authTokenDao(Connection connection) {this.connection = connection;}

    /**
     * Insert auth token into database
     * @param token model token object
     * @throws DatabaseException
     */

    public void insert(authToken token) throws DatabaseException {

        String sql = "INSERT into AuthToken (ID, userID) VALUES (?,?)";

        if(token.getAssocaitedUserID() == null || token.getAssocaitedUserID() == ""){
            throw new DatabaseException("Invalid auth token passed");
        }

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, token.getTokenID() );
            stmt.setString(2, token.getAssocaitedUserID() );
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error in inserting auth token");
        }
    }

    /**
     * edit token data in database
     * @param tokenID id for token to edit
     * @param newToken new token value for given token
     */

    public void editTokenID(String tokenID, String newToken){}

    public void clear(String associatedUserID) throws DatabaseException {
        String sql = "DELETE FROM AuthToken WHERE userID = ?";
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, associatedUserID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error occurred while clearing auth token database: " + e);
        }
    }

    /**
     * clear database of tokens
     */
    public void clear() throws DatabaseException {
        String sql = "DELETE FROM AuthToken";
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error occurred while clearing auth token database: " + e);
        }
    }


    /**
     * find token from database and return model token object
     * @param tokenID token id
     * @return model token object
     * @throws DatabaseException
     */

    public authToken find(String tokenID) throws DatabaseException{

        authToken token = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthToken WHERE ID = ?;";
        try(PreparedStatement stmt = connection.prepareStatement(sql) ){

            stmt.setString(1, tokenID);
            rs = stmt.executeQuery();
            if(rs.next()){
                token = new authToken(rs.getString("ID"), rs.getString("userID"));
                return token;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error in finding auth token");
        } finally {
            if(rs != null){
                try{
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return token;
    }

    public List<authToken> findTokens(String associatedUsername) throws DatabaseException{

        List<authToken> returnList = new ArrayList<authToken>();
        authToken token = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthToken WHERE userID = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql) ){
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();
            while(rs.next()){
                returnList.add(new authToken(rs.getString("ID"), rs.getString("userID")));

            }
            return returnList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error in finding auth token");
        } finally {
            if(rs != null){
                try{
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<authToken> findTokens() throws DatabaseException{

        List<authToken> returnList = new ArrayList<authToken>();
        authToken token = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthToken";
        try(PreparedStatement stmt = connection.prepareStatement(sql) ){
            rs = stmt.executeQuery();
            while(rs.next()){
                returnList.add(new authToken(rs.getString("ID"), rs.getString("userID")));

            }
            return returnList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error in finding auth token");
        } finally {
            if(rs != null){
                try{
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * get associated user associated with token id
     * @param tokenID token id
     * @return
     */


}
