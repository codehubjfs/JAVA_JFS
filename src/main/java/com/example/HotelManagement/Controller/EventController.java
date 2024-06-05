package com.example.HotelManagement.Controller;

import com.example.HotelManagement.DTO.MessageResponse;
import com.example.HotelManagement.Events.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class EventController {

    private final CreateEvent createEvent;
    private final BuyTicket buyTicket;
    private final ViewEvents viewEvents;

    @Autowired
    public EventController(CreateEvent createEvent, BuyTicket buyTicket, ViewEvents viewEvents) {
        this.createEvent = createEvent;
        this.buyTicket = buyTicket;
        this.viewEvents = viewEvents;
    }

    @PostMapping("/createTrainingProgram")
    public MessageResponse createTrainingProgram(@RequestBody CreateEventDTO createEventDTO) {
        return createEvent.createTrainingProgram(
                createEventDTO.getEventName(),
                createEventDTO.getLocationName(),
                createEventDTO.getStartDate(),
                createEventDTO.getEndDate(),
                createEventDTO.getMinAge(),
                createEventDTO.getQuota(),
                createEventDTO.getDescription(),
                createEventDTO.getMgrId()
        );
    }

    @PostMapping("/createActivity")
    public MessageResponse createActivity(@RequestBody CreateGuestActivityDTO createGuestActivityDTO) {
        return createEvent.createActivity(
                createGuestActivityDTO.getEventName(),
                createGuestActivityDTO.getLocationName(),
                createGuestActivityDTO.getStartDate(),
                createGuestActivityDTO.getEndDate(),
                createGuestActivityDTO.getMinAge(),
                createGuestActivityDTO.getQuota(),
                createGuestActivityDTO.getDescription(),
                createGuestActivityDTO.getMgrId(),
                createGuestActivityDTO.getPrice()
        );
    }

    @PostMapping("/createGroupTour")
    public MessageResponse createGroupTours(@RequestBody CreateGroupToursDTO createGroupToursDTO) {
        return createEvent.createGroupTours(
                createGroupToursDTO.getEventName(),
                createGroupToursDTO.getLocationName(),
                createGroupToursDTO.getStartDate(),
                createGroupToursDTO.getEndDate(),
                createGroupToursDTO.getMinAge(),
                createGroupToursDTO.getQuota(),
                createGroupToursDTO.getDescription(),
                createGroupToursDTO.getMgrId(),
                createGroupToursDTO.getPrice(),
                createGroupToursDTO.getOrganizerName(),
                createGroupToursDTO.getTourVehicle(),
                createGroupToursDTO.getDistanceToCover()
        );
    }

    @PostMapping("/buyTicket/{eventId}/{guestId}")
    public MessageResponse buyTicket(@PathVariable(name = "guestId") int guestId, @PathVariable(name = "eventId") int eventId) throws Exception {
        return buyTicket.buyTicket(guestId, eventId);
    }

    /**
     * A housekeeper applies to a training program
     * @param hkId housekeeper id
     * @param eventId event id
     * @return Message response
     * @throws Exception exception
     */
    @PostMapping("/applyAsHK/{eventId}/{hkId}")
    public MessageResponse applyAsHousekeeper(@PathVariable(name = "hkId") int hkId, @PathVariable(name = "eventId") int eventId) throws Exception {
        return buyTicket.applyAsHousekeeper(hkId, eventId);
    }

    /**
     * A security staff applies to a training program
     * @param ssId security staff
     * @param eventId event id
     * @return Message response
     * @throws Exception exception
     */
    @PostMapping("/applyAsSS/{eventId}/{ssId}")
    public MessageResponse applyAsSecurityStaff(@PathVariable(name = "ssId") int ssId, @PathVariable(name = "eventId") int eventId) throws Exception {
        return buyTicket.applyAsSecurityStaff(ssId, eventId);
    }

    @PostMapping("/evaluateHKApplication")
    public MessageResponse evaluateHKApplication(@RequestBody EvaluateEmployeeDTO dto) {
        return buyTicket.evaluateHKApplication(dto.getEmployeeId(), dto.getEventId(), dto.getMgrId(), dto.getStatus());
    }

    @PostMapping("/evaluateSSApplication")
    public MessageResponse evaluateSSApplication(@RequestBody EvaluateEmployeeDTO dto) {
        return buyTicket.evaluateSSApplication(dto.getEmployeeId(), dto.getEventId(), dto.getMgrId(), dto.getStatus());
    }

    @GetMapping("/viewGuestActivity/{eventId}")
    public ViewGroupTourDTO viewGuestActivity(@PathVariable(name = "eventId") int eventId) {
        return viewEvents.viewGuestActivity(eventId);
    }

    @GetMapping("/viewAllGuestActivities")
    public ViewAllGroupToursDTO viewAllGuestActivities() {
        return viewEvents.viewAllGuestActivities();
    }

    @GetMapping("/viewGuestActivitiesByName")
    public ViewAllGroupToursDTO viewGuestActivitiesByName(@RequestBody SearchEventDTO dto) {
        return viewEvents.viewGuestActivitiesByName(dto.getEventName(), dto.getLowerLimit(), dto.getUpperLimit());
    }

    @GetMapping("/viewTrainingProgram/{eventId}")
    public ViewTrainingProgramDTO viewTrainingProgram(@PathVariable(name = "eventId") int eventId) {
        return viewEvents.viewTrainingProgram(eventId);
    }

    @GetMapping("/viewAllTrainingPrograms")
    public ViewAllGroupToursDTO viewAllTrainingPrograms() {
        return viewEvents.viewAllTrainingPrograms();
    }

    @GetMapping("/viewAllTrainingPrograms/{mgrId}")
    public ViewAllGroupToursDTO viewAllTrainingPrograms(@PathVariable(name = "mgrId") int mgrId) {
        return viewEvents.viewAllTrainingPrograms(mgrId);
    }
}
