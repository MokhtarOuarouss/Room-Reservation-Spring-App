package fr.norsys.room_reservation.service;

import fr.norsys.room_reservation.entity.Room;
import fr.norsys.room_reservation.exception.RoomException;
import fr.norsys.room_reservation.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RoomService {

    RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public Room saveRoom(Room room) {
        return  roomRepository.save(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(long id) {
        Optional<Room> room = roomRepository.findById(id);;
        if (room.isEmpty()){
            throw new RoomException("Room id not exist  : "+id);
        }
        return room;
    }

    public void DeleteRoom(long id) {
        Optional<Room> roomDeleted = roomRepository.findById(id);

        if (roomDeleted.isEmpty()) {
            throw new RoomException("Room id not exist  : "+id);
        }
        roomRepository.delete(roomDeleted.get());
    }

    public List<Room> getAvailableRooms() {
        List<Room> allRooms = roomRepository.findAll();
        return allRooms.stream()
                .filter(room -> isAvailableRoom(room.getId()))
                .collect(Collectors.toList());
    }

    public boolean isAvailableRoom(Long id) {
        Room room = roomRepository.findById(id).orElse(null);
        return room != null && room.getCapacite() > roomRepository.CountReservations(id);
    }


}
