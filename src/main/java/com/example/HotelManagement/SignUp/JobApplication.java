package com.example.HotelManagement.SignUp;

import com.example.HotelManagement.DTO.MessageResponse;
import com.example.HotelManagement.DTO.MessageType;
import com.example.HotelManagement.Database.DatabaseConnection;
import com.example.HotelManagement.Events.CreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class JobApplication {

    //position names
    private final String HOUSEKEEPER = "HOUSEKEEPER";
    private final String SECURITY = "SECURITY STAFF";
    private final String RECEPTIONIST = "RECEPTIONIST";
    private final String RECRUITER = "RECRUITER";
    private final String MANAGER = "MANAGER";

    private DatabaseConnection databaseConnection;
    private UserFetch userFetch;
    private CreateEvent createEvent;
    private UserInsertion userInsertion;

    @Autowired
    public JobApplication(DatabaseConnection databaseConnection, UserFetch userFetch, CreateEvent createEvent, UserInsertion userInsertion) {
        this.databaseConnection = databaseConnection;
        this.userFetch = userFetch;
        this.createEvent = createEvent;
        this.userInsertion = userInsertion;
    }

    /**
     * Method creates a job application for a candidate
     * @param candidateId candidate id
     * @param position position that the candidate is applying for
     * @return message response
     */
    public MessageResponse createJobApplication(int candidateId, String position) {
        String query;

        //preconditions
        if(!createEvent.entryExists("Candidate", candidateId, "id", null))
            return new MessageResponse("No such Candidate.", MessageType.ERROR);

        position = position.toUpperCase(Locale.ENGLISH);
        if(!position.equals(HOUSEKEEPER) && !position.equals(SECURITY) && !position.equals(RECEPTIONIST) &&
                !position.equals(RECRUITER) && !position.equals(MANAGER)) {
            return new MessageResponse("Invalid position. Valid positions are: " + HOUSEKEEPER + ", " +
                    SECURITY + ", " + RECEPTIONIST + ", " + RECRUITER + ", " + MANAGER + ".", MessageType.ERROR);
        }

        if(jobEntryExists("Job_Application", candidateId, position))
            return new MessageResponse("You have already applied to this position.", MessageType.ERROR);

        query = "INSERT INTO Job_Application VALUES (" + candidateId + ", '" + position + "', 'PENDING');";
        if(userInsertion.executeUpdate(query).getMessageType().equals(MessageType.ERROR)){
            return new MessageResponse("Insertion into Job Application failed",MessageType.ERROR);
        }

        return new MessageResponse("Job Application successful.", MessageType.SUCCESS);
    }

    /**
     * Recruiter rejects a candidate's job application
     * @param candidateId candidate id
     * @param position position
     * @param recruiterId recruiter id
     * @return message response
     */
    public MessageResponse rejectJobApplication(int candidateId, String position, int recruiterId) {
        String query;

        position = position.toUpperCase(Locale.ENGLISH);
        if(!position.equals(HOUSEKEEPER) && !position.equals(SECURITY) && !position.equals(RECEPTIONIST) &&
                !position.equals(RECRUITER) && !position.equals(MANAGER)) {
            return new MessageResponse("Invalid position. Valid positions are: " + HOUSEKEEPER + ", " +
                    SECURITY + ", " + RECEPTIONIST + ", " + RECRUITER + ", " + MANAGER + ".", MessageType.ERROR);
        }

        if(!jobEntryExists("Job_Application", candidateId, position))
            return new MessageResponse("Job application not found.", MessageType.ERROR);

        if(!createEvent.entryExists("Recruiter", recruiterId, "id", null))
            return new MessageResponse("No such Recruiter.", MessageType.ERROR);

        if(jobEntryExists("Approves", candidateId, position))
            return new MessageResponse("Job application already evaluated.", MessageType.ERROR);

        query = "INSERT INTO Approves VALUES (" + candidateId + ", '" + position + "', " + recruiterId + ");";
        if(userInsertion.executeUpdate(query).getMessageType().equals(MessageType.ERROR)){
            return new MessageResponse("Insertion into Approves table failed.",MessageType.ERROR);
        }

        query = "UPDATE Job_Application\n" +
                "SET status = 'REJECTED'\n" +
                "WHERE id = " + candidateId + " AND position = '" + position + "';";
        if(userInsertion.executeUpdate(query).getMessageType().equals(MessageType.ERROR)){
            return new MessageResponse("Update of Job_Application status failed.",MessageType.ERROR);
        }

        return new MessageResponse("Candidate evaluation successful.", MessageType.SUCCESS);
    }

    /**
     * Recruiter approves a candidate's job application
     * @param candidateId candidate id
     * @param position position
     * @param recruiterId recruiter id
     * @return message response
     */
    public MessageResponse approveJobApplication(int candidateId, String position, int recruiterId) {
        String query;
        MessageResponse promotionResponse;

        position = position.toUpperCase(Locale.ENGLISH);
        if(!position.equals(HOUSEKEEPER) && !position.equals(SECURITY) && !position.equals(RECEPTIONIST) &&
                !position.equals(RECRUITER) && !position.equals(MANAGER)) {
            return new MessageResponse("Invalid position. Valid positions are: " + HOUSEKEEPER + ", " +
                    SECURITY + ", " + RECEPTIONIST + ", " + RECRUITER + ", " + MANAGER + ".", MessageType.ERROR);
        }

        if(!jobEntryExists("Job_Application", candidateId, position))
            return new MessageResponse("Job application not found.", MessageType.ERROR);

        if(!createEvent.entryExists("Recruiter", recruiterId, "id", null))
            return new MessageResponse("No such Recruiter.", MessageType.ERROR);

        if(jobEntryExists("Approves", candidateId, position))
            return new MessageResponse("Job application already evaluated.", MessageType.ERROR);

        query = "INSERT INTO Approves VALUES (" + candidateId + ", '" + position + "', " + recruiterId + ");";
        if(userInsertion.executeUpdate(query).getMessageType().equals(MessageType.ERROR)){
            return new MessageResponse("Insertion into Approves table failed.",MessageType.ERROR);
        }

        query = "UPDATE Job_Application\n" +
                "SET status = 'APPROVED'\n" +
                "WHERE id = " + candidateId + " AND position = '" + position + "';";
        if(userInsertion.executeUpdate(query).getMessageType().equals(MessageType.ERROR)){
            return new MessageResponse("Update of Job_Application status failed.",MessageType.ERROR);
        }

        switch (position) {
            case HOUSEKEEPER:
                promotionResponse = userInsertion.promoteToHousekeeper(candidateId);
                break;
            case SECURITY:
                promotionResponse = userInsertion.promoteToSecurityStaff(candidateId);
                break;
            case RECRUITER:
                promotionResponse = userInsertion.promoteToRecruiter(candidateId);
                break;
            case MANAGER:
                promotionResponse = userInsertion.promoteToManager(candidateId);
                break;
            default:
                promotionResponse = userInsertion.promoteToReceptionist(candidateId);
                break;
        }

        if(promotionResponse.getMessageType().equals(MessageType.ERROR))
            return promotionResponse;

        return new MessageResponse("Candidate evaluation successful.", MessageType.SUCCESS);
    }

    /**
     * Returns information about a particular job application
     * @param candidateId candidate id
     * @param position position
     * @return dto object
     */
    public ViewCandidateDTO viewJobApplication(int candidateId, String position) {
        String query;
        ViewCandidateDTO dto = null;

        position = position.toUpperCase(Locale.ENGLISH);

        //precondition
        if(!jobEntryExists("Job_Application", candidateId, position))
            throw new IllegalArgumentException("Job Application not found.");

        if(createEvent.entryExists("Candidate", candidateId, "id", null)) {
            query = "SELECT *\n" +
                    "FROM Users NATURAL JOIN Candidate NATURAL JOIN Job_Application\n" +
                    "WHERE id = " + candidateId + " AND position = '" + position + "';";
        }
        else {
            query = "SELECT *\n" +
                    "FROM Users NATURAL JOIN Job_Application\n" +
                    "WHERE id = " + candidateId + " AND position = '" + position + "';";
        }

        Object[] resultArr = null;
        resultArr = databaseConnection.execute(query, DatabaseConnection.FETCH);
        ResultSet resultSet = (ResultSet) resultArr[0];
        Connection connection = (Connection) resultArr[1];

        try {
            if(!resultSet.next())
                throw new IllegalArgumentException("Job Application could not be fetched.");

            if(createEvent.entryExists("Candidate", candidateId, "id", null)) {
                dto = new ViewCandidateDTO(
                        resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        resultSet.getString("gender"),
                        resultSet.getLong("date_of_birth"),
                        resultSet.getString("cover_letter"),
                        resultSet.getString("position"),
                        resultSet.getString("status"),
                        "",                                         //intentionally left blank
                        ""                                         //intentionally left blank
                );
            }
            else {
                dto = new ViewCandidateDTO(
                        resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        resultSet.getString("gender"),
                        resultSet.getLong("date_of_birth"),
                        "",                                         //intentionally left blank
                        resultSet.getString("position"),
                        resultSet.getString("status"),
                        "",                                         //intentionally left blank
                        ""                                         //intentionally left blank
                );
            }

            connection.close();
        }
        catch ( Exception e ){
            try {
                connection.close();
            }catch (Exception e1 ){
                throw new IllegalArgumentException("Connection failure.");
            }
            throw new IllegalArgumentException("Connection failure.");
        }

        return dto;
    }

    /**
     * Lists all the job applications
     * @return dto object
     */
    public ViewAllCandidatesDTO viewAllJobApplications() {
        List<ViewCandidateDTO> dtoList = new ArrayList<>();
        String query;

        query = "SELECT *\n" +
                "FROM Users NATURAL JOIN Job_Application\n" +
                "ORDER BY CASE WHEN status = 'PENDING' THEN 1\n" +
                "              WHEN status = 'APPROVED' THEN 2\n" +
                "              ELSE 3\n" +
                "         END ASC;";

        Object[] resultArr = null;
        resultArr = databaseConnection.execute(query, DatabaseConnection.FETCH);
        ResultSet resultSet = (ResultSet) resultArr[0];
        Connection connection = (Connection) resultArr[1];

        try {
            while (resultSet.next()) {
                ViewCandidateDTO dto = new ViewCandidateDTO(
                        resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        resultSet.getString("gender"),
                        resultSet.getLong("date_of_birth"),
                        "",                                         //intentionally left blank
                        resultSet.getString("position"),
                        resultSet.getString("status"),
                        "",                                         //intentionally left blank
                        ""                                         //intentionally left blank
                );
                dtoList.add(dto);
            }
            connection.close();
        }
        catch ( Exception e ){
            try {
                connection.close();
            }catch (Exception e1 ){
                throw new IllegalArgumentException("Connection failure.");
            }
            throw new IllegalArgumentException("Connection failure.");
        }

        return new ViewAllCandidatesDTO(dtoList);
    }

    /**
     * Lists all the applications of the given candidate
     * @param candidateId candidate id
     * @return dto object
     */
    public ViewAllCandidatesDTO viewMyJobApplications(int candidateId) {
        List<ViewCandidateDTO> dtoList = new ArrayList<>();
        String query;

        query = "SELECT *\n" +
                "FROM Users NATURAL JOIN Job_Application\n" +
                "WHERE id = " + candidateId + " AND status = 'PENDING';";

        Object[] resultArr = null;
        resultArr = databaseConnection.execute(query, DatabaseConnection.FETCH);
        ResultSet resultSet = (ResultSet) resultArr[0];
        Connection connection = (Connection) resultArr[1];

        try {
            while (resultSet.next()) {
                ViewCandidateDTO dto = new ViewCandidateDTO(
                        resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        resultSet.getString("gender"),
                        resultSet.getLong("date_of_birth"),
                        "",                                         //intentionally left blank
                        resultSet.getString("position"),
                        resultSet.getString("status"),
                        "",
                        ""
                );
                dtoList.add(dto);
            }
            connection.close();
        }
        catch ( Exception e ){
            try {
                connection.close();
            }catch (Exception e1 ){
                throw new IllegalArgumentException("Connection failure.");
            }
            throw new IllegalArgumentException("Connection failure.");
        }

        query = "SELECT u.id, u.firstname, u.lastname, u.email, u.password, u.phone, u.address, u.gender, u.date_of_birth,\n" +
                "       c.cover_letter, ja.position, ja.status, u2.firstname, u2.lastname\n" +
                "FROM Users u NATURAL JOIN Candidate c NATURAL JOIN Job_Application ja, Approves a, Users u2\n" +
                "WHERE u.id = " + candidateId + " AND ja.id = a.candidate_id AND ja.position = a.position AND a.recruiter_id = u2.id\n" +
                "ORDER BY CASE WHEN status = 'PENDING' THEN 1\n" +
                "              WHEN status = 'APPROVED' THEN 2\n" +
                "              ELSE 3\n" +
                "         END ASC;";

        resultArr = databaseConnection.execute(query, DatabaseConnection.FETCH);
        resultSet = (ResultSet) resultArr[0];
        connection = (Connection) resultArr[1];

        try {
            while (resultSet.next()) {
                ViewCandidateDTO dto = new ViewCandidateDTO(
                        resultSet.getInt("id"),
                        resultSet.getString("u.firstname"),
                        resultSet.getString("u.lastname"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        resultSet.getString("gender"),
                        resultSet.getLong("date_of_birth"),
                        "",                                         //intentionally left blank
                        resultSet.getString("position"),
                        resultSet.getString("status"),
                        resultSet.getString("u2.firstname"),
                        resultSet.getString("u2.lastname")
                );
                dtoList.add(dto);
            }
            connection.close();
        }
        catch ( Exception e ){
            try {
                connection.close();
            }catch (Exception e1 ){
                throw new IllegalArgumentException("Connection failure.");
            }
            throw new IllegalArgumentException("Connection failure.");
        }

        return new ViewAllCandidatesDTO(dtoList);
    }

    /**
     * Checks if an entry with the given attributes exists in the Job_Application table
     * @param candidateId candidate id
     * @param position position
     * @return boolean
     */
    private boolean jobEntryExists(String tableName, int candidateId, String position) {
        boolean result;
        String query = "SELECT *\n" +
                "FROM " + tableName + "\n" +
                "WHERE id = " + candidateId + " AND position = '" + position + "';";

        Object[] resultArr = null;
        resultArr = databaseConnection.execute(query, DatabaseConnection.FETCH);
        ResultSet resultSet = (ResultSet) resultArr[0];
        Connection connection = (Connection) resultArr[1];

        try {
            result = resultSet.next();
        }
        catch(Exception e) {
            try {
                connection.close();
            }
            catch (Exception e1) {
                throw new IllegalArgumentException("Error when closing the connection");        //SKETCHY
            }
            return false;
        }
        try {
            connection.close();
        }
        catch (Exception e1) {
            throw new IllegalArgumentException("Error when closing the connection");        //SKETCHY
        }
        return result;
    }
}
