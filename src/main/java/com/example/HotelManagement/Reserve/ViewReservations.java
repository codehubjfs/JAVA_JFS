package com.example.HotelManagement.Reserve;

import com.example.HotelManagement.DTO.MessageResponse;
import com.example.HotelManagement.DTO.MessageType;
import com.example.HotelManagement.Database.DatabaseConnection;
import com.example.HotelManagement.SignUp.UserFetch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class ViewReservations {

    private final DatabaseConnection databaseConnection;
    private final UserFetch userFetch;

    @Autowired
    public ViewReservations(DatabaseConnection databaseConnection, UserFetch userFetch) {
        this.databaseConnection = databaseConnection;
        this.userFetch = userFetch;
    }

    /**
     * View all reservations
     * @return
     * @throws Exception
     */
    public List<ReservationDTO> viewAllReservations() throws Exception {

        List<ReservationDTO> reservations = new ArrayList<>();

        String query = "SELECT * FROM (Reservation res NATURAL JOIN Room r NATURAL JOIN Room_Type rt), Building b" +
                " , (Guests g NATURAL JOIN Users u ) WHERE res.guest_id = g.id AND b.building_no = r.building_no";
        Object[] resultArr = null;
        resultArr = databaseConnection.execute(query,DatabaseConnection.FETCH);

        ResultSet rs = (ResultSet) resultArr[0];
        Connection connection = (Connection) resultArr[1];

        try {
            while ( rs.next()){
                ReservationDTO reservationDTO =
                        new ReservationDTO(
                                rs.getInt("res.reservation_id"),
                                rs.getString("b.name"),
                                rs.getString("b.location_name"),
                                rs.getLong("res.check_in_date"),
                                rs.getLong("res.check_out_date"),
                                rs.getString("r.type"),
                                rs.getString("r.description"),
                                rs.getDouble("rt.price"),
                                rs.getInt("rt.no_of_people"),
                                rs.getString("u.firstname"),
                                rs.getDouble("g.money_spent")
                        );

                try{
                    reservations.add(reservationDTO);
                }catch (Exception e){
                    throw new Exception("Result set read problem");
                }
            }
            connection.close();
        }
        catch ( Exception e ){
            try {
                connection.close();
            }catch (Exception e1 ){
                throw new Exception("Cannot close connection");
            }
            throw new Exception("cannot do the whole fetching process");
        }

        return reservations;
    }

    /**
     * Views the reservation of the guest
     * @param guestId
     * @return
     * @throws Exception
     */
    public ReservationDTO viewReservation(int guestId ) throws Exception {
        ReservationDTO reservation = null;

        String query = "SELECT * FROM (Reservation as res NATURAL JOIN Room as r NATURAL JOIN Room_Type as rt), Building as b," +
                "(Guests as g NATURAL JOIN Users as u ) WHERE b.building_no = r.building_no AND res.guest_id = g.id AND g.id = " + guestId;
        Object[] resultArr = null;
        resultArr = databaseConnection.execute(query,DatabaseConnection.FETCH);

        ResultSet rs = (ResultSet) resultArr[0];
        Connection connection = (Connection) resultArr[1];

        try {
            rs.next();
                reservation =
                        new ReservationDTO(
                                rs.getInt("res.reservation_id"),
                                rs.getString("b.name"),
                                rs.getString("b.location_name"),
                                rs.getLong("res.check_in_date"),
                                rs.getLong("res.check_out_date"),
                                rs.getString("r.type"),
                                rs.getString("r.description"),
                                rs.getDouble("rt.price"),
                                rs.getInt("rt.no_of_people"),
                                rs.getString("u.firstname"),
                                rs.getDouble("g.money_spent")
                        );
            connection.close();
        }
        catch ( Exception e ){
            try {
                connection.close();
            }catch (Exception e1 ){
                throw new Exception("Cannot close connection");
            }
            throw new Exception("rs problem errors or no such guest");
        }

        return reservation;
    }


    /**
     * Gets the available rooms
     * @return
     * @throws Exception
     */
    public List<RoomDTO> getAllEmptyRooms() throws Exception {

        List<RoomDTO> rooms = new ArrayList<>();

        Long time = System.currentTimeMillis();
        String query = "SELECT * FROM ( Room as r NATURAL JOIN Room_Type rt ), Building as b" +
                " where b.building_no = r.building_no AND (r.room_no,r.building_no) NOT IN ("+
                "SELECT res1.room_no,res1.building_no FROM Reservation as res1 " +
                "WHERE res1.check_out_date > " + time +
                " AND res1.check_in_date < " + time + " );";

        Object[] resultArr = null;
        resultArr = databaseConnection.execute(query,DatabaseConnection.FETCH);

        ResultSet rs = (ResultSet) resultArr[0];
        Connection connection = (Connection) resultArr[1];

        try {
            while ( rs.next()){
                RoomDTO roomDTO =
                        new RoomDTO(
                                rs.getInt("r.room_no"),
                                rs.getString("b.name"),
                                rs.getString("b.location_name"),
                                rs.getString("r.type"),
                                rs.getString("r.description"),
                                rs.getDouble("rt.price"),
                                rs.getInt("rt.no_of_people"),
                                rs.getInt("b.no_of_rooms"),
                                rs.getInt("b.no_of_floors")
                        );

                try{
                    rooms.add(roomDTO);
                }catch (Exception e){
                    throw new Exception("Result set read problem");
                }
            }
            connection.close();
        }
        catch ( Exception e ){
            try {
                connection.close();
            }catch (Exception e1 ){
                throw new Exception("Cannot close connection");
            }
            throw new Exception("cannot do the whole fetching process");
        }

        return rooms;
    }

    public List<String> viewRoomTypes(MakeReservationDTO makeReservationDTO) throws Exception {

        List<String> arr = new ArrayList<>();

        String query = "SELECT * FROM ( Room as r NATURAL JOIN Room_Type rt NATURAL JOIN Reservation res)" +
                " WHERE (r.room_no,r.building_no) NOT IN ("+
                "SELECT res1.room_no,res1.building_no FROM Reservation as res1 " +
                "WHERE (res1.check_out_date >= " + makeReservationDTO.getCheckInDate() +
                " AND res1.check_in_date <= " + makeReservationDTO.getCheckInDate() + " ) OR (res1.check_out_date >= " + makeReservationDTO.getCheckOutDate() +
                " AND res1.check_in_date <= " + makeReservationDTO.getCheckOutDate() + " ) OR (res1.check_out_date <= " + makeReservationDTO.getCheckOutDate() +
                " AND res1.check_in_date >= " + makeReservationDTO.getCheckInDate() + ") );";

        Object[] resultArr = null;
        resultArr = databaseConnection.execute(query,DatabaseConnection.FETCH);

        ResultSet rs = (ResultSet) resultArr[0];
        Connection connection = (Connection) resultArr[1];

        try {
            while ( rs.next()){
                String a = rs.getString("r.type");
                if( !arr.contains(a)) {
                    arr.add(a);
                }
            }
            connection.close();
        }
        catch ( Exception e ){
            try {
                connection.close();
            }catch (Exception e1 ){
                throw new Exception("Cannot close connection");
            }
            throw  new Exception("cannot do the whole fetching process");
        }

        return arr;
    }
}
