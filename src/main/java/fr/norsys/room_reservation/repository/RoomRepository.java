package fr.norsys.room_reservation.repository;

import fr.norsys.room_reservation.entity.Room;
import fr.norsys.room_reservation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.room.id = :id")
    int CountReservations(@Param("id") Long id);


    @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT res.room.id FROM Reservation res)")
    List<Room> findAvailableRooms();
}
