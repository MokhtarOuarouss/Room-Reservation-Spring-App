package fr.norsys.room_reservation.controller;


import fr.norsys.room_reservation.entity.Reservation;
import fr.norsys.room_reservation.entity.Room;
import fr.norsys.room_reservation.entity.User;
import fr.norsys.room_reservation.exception.ReservationException;
import fr.norsys.room_reservation.exception.RoomException;
import fr.norsys.room_reservation.service.ReservationService;
import fr.norsys.room_reservation.service.RoomService;
import fr.norsys.room_reservation.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    @Autowired
    private  ReservationService reservationService;
    @Autowired
    private  RoomService roomService;
    @Autowired
    private UserService userService;



    @PostMapping
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody Reservation reservation) {
        Optional<User> existingUser = userService.getUserById(reservation.getUser().getId());
        Optional<Room> existingRoom = roomService.getRoomById(reservation.getRoom().getId());

        if (existingUser.isEmpty()) {
            User newUser = new User();
            newUser.setName(reservation.getUser().getName());
            userService.saveUser(newUser);
            reservation.setUser(newUser);
        } else {
            reservation.setUser(existingUser.get());
        }
        if (existingRoom.isEmpty()) {
            Room newRoom = new Room();
            newRoom.setCapacite(reservation.getRoom().getCapacite());
            roomService.saveRoom(newRoom);
            reservation.setRoom(newRoom);
        } else {
            if (!roomService.isAvailableRoom(reservation.getRoom().getId()))
                throw new ReservationException(HttpStatus.BAD_REQUEST, "Room is full");
            reservation.setRoom(existingRoom.get());
        }



        Reservation savedReservation = reservationService.saveReservation(reservation);
        return new ResponseEntity<>(savedReservation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservationById(id)
                .orElseThrow(() -> new ReservationException(HttpStatus.NOT_FOUND, "Reservation not found"));
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @Valid @RequestBody Reservation updatedReservation) {
        Reservation existingReservation = reservationService.getReservationById(id)
                .orElseThrow(() -> new ReservationException(HttpStatus.NOT_FOUND, "Reservation not found"));

        existingReservation.setUser(updatedReservation.getUser());
        existingReservation.setRoom(updatedReservation.getRoom());
        existingReservation.setStartTime(updatedReservation.getStartTime());
        existingReservation.setEndTime(updatedReservation.getEndTime());

        Reservation savedReservation = reservationService.saveReservation(existingReservation);
        return new ResponseEntity<>(savedReservation, HttpStatus.OK);
    }

}
