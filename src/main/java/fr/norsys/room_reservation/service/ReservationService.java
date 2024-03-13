package fr.norsys.room_reservation.service;


import fr.norsys.room_reservation.entity.Reservation;
import fr.norsys.room_reservation.exception.ReservationException;
import fr.norsys.room_reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isEmpty()) {
            throw new ReservationException(HttpStatus.NOT_FOUND,"Reservation not found with ID: " + id);
        }
        return reservation;
    }

    public void deleteReservation(Long id) {
        Optional<Reservation> reservationDeleted = reservationRepository.findById(id);

        if (reservationDeleted.isEmpty()) {
            throw new ReservationException(HttpStatus.NOT_FOUND,"Reservation not found with ID: " + id);
        }
        reservationRepository.delete(reservationDeleted.get());
    }


}

