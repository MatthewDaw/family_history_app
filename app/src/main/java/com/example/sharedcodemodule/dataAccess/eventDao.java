package com.example.sharedcodemodule.dataAccess;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.example.sharedcodemodule.model.event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * event interaction with database
 * @param <T> generic variable
 */

public class eventDao<T> {


    private Connection connection = null;

    public eventDao(Connection connection)  {
        this.connection = connection;
    }


    /**
     * get specific event object by event id
     *
     * @param eventID id of event
     * @return event object
     */
    public event find(String eventID) throws DatabaseException {

        event event = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM events WHERE eventID = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new event(rs.getString("eventID"),
                        rs.getString("associatedUserName"),
                        rs.getString("personID"),
                        Float.valueOf(rs.getString("latitude")),
                        Float.valueOf(rs.getString("longitude")),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("eventType"),
                        Integer.valueOf(rs.getString("year")));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error encountered while finding event");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return event;
        }

    }



    /**
     * get all events associated with user
     *
     * @param userName username for user
     * @return list of event objects
     */

    public List<event> getEvents(String userName) throws DatabaseException {
        List<event> returnEvents = new ArrayList<event>();
        event event = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM events WHERE associatedUserName = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                event = new event(rs.getString("eventID"),
                        rs.getString("associatedUserName"),
                        rs.getString("personID"),
                        Float.valueOf(rs.getString("latitude")),
                        Float.valueOf(rs.getString("longitude")),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("eventType"),
                        Integer.valueOf(rs.getString("year")));
                returnEvents.add(event);
                //return returnEvents;
                //return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error encountered while finding event");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return returnEvents;
        }

    }

    /**
     * get all events
     *
     * @return list of event objects
     */

    public List<event> getEvents() throws DatabaseException {
        List<event> returnEvents = new ArrayList<event>();
        event event = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM events";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            rs = stmt.executeQuery();
            while (rs.next()) {
                event = new event(rs.getString("eventID"),
                        rs.getString("associatedUserName"),
                        rs.getString("personID"),
                        Float.valueOf(rs.getString("latitude")),
                        Float.valueOf(rs.getString("longitude")),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("eventType"),
                        Integer.valueOf(rs.getString("year")));
                returnEvents.add(event);
                //return returnEvents;
                //return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error encountered while finding event");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return returnEvents;
        }

    }
    /**
     * takes given event object and adds it to database
     *
     * @param event event object
     */

    public void insert(event event) throws DatabaseException {

        String sql = "INSERT into events (eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year) VALUES (?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUserName());
            stmt.setString(3, event.getPersonID());
            stmt.setString(4, String.valueOf(event.getLatitude()));
            stmt.setString(5, String.valueOf(event.getLongitude()));
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setString(9, String.valueOf(event.getYear()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error inserting into event table");
        }
    }

    /**
     * adds a list of events to database
     *
     * @param events list of event objects
     */

    public void addEvents(List<event> events) throws DatabaseException {

        for (int i = 0; i < events.size(); i++) {
            event event = events.get(i);
            String sql = "INSERT into events (eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year) VALUES (?,?,?,?,?,?,?,?,?)";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, event.getEventID());
                stmt.setString(2, event.getAssociatedUserName());
                stmt.setString(3, event.getPersonID());
                stmt.setString(4, String.valueOf(event.getLatitude()));
                stmt.setString(5, String.valueOf(event.getLongitude()));
                stmt.setString(6, event.getCountry());
                stmt.setString(7, event.getCity());
                stmt.setString(8, event.getEventType());
                stmt.setString(9, String.valueOf(event.getYear()));
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException("Error inserting into event table");
            }
        }
    }

    /**
     * Delete all events associated with user
     *
     * @param associatedUserName id of user
     */

    public void clear(String associatedUserName) throws DatabaseException {
        String sql = "DELETE FROM events WHERE associatedUserName = ?";
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, associatedUserName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error occurred while clearing event database: " + e);
        }
    }

    /**
     * clear database of events
     */

    public void clear() throws DatabaseException {
        String sql = "DELETE FROM events";
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error occurred while clearing event database: " + e);
        }
    }
}
