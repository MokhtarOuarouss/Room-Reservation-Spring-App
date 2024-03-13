package fr.norsys.room_reservation.exception;

import org.springframework.http.HttpStatus;

public class ReservationException extends RuntimeException{

    public ReservationException(HttpStatus http ,String  message) {
        super(message);
    }

}
