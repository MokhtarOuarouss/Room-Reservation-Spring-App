package fr.norsys.room_reservation.controller;


import fr.norsys.room_reservation.entity.Room;
import fr.norsys.room_reservation.exception.RoomException;
import fr.norsys.room_reservation.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService ;

    @PostMapping
    public ResponseEntity<String> CreateRoom(@RequestBody @Valid Room room){

        roomService.saveRoom(room);
        return ResponseEntity.ok("Room created successfully");
    }
    @GetMapping
    public List<Room> getAllRoom(){
        return roomService.getAllRooms();

    }

    @GetMapping("/available")
    public List<Room> getAvailableRooms() {
        return roomService.getAvailableRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Room>> getRoomById(@PathVariable int  id){

        return ResponseEntity.ok(roomService.getRoomById(id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        roomService.DeleteRoom(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRoom(@PathVariable int id, @RequestBody @Valid Room updateRoom) throws Exception {


        Optional<Room> existingRoomOptional = roomService.getRoomById(id);
        if (existingRoomOptional.isPresent()) {
            Room existingRoom = existingRoomOptional.get();

            existingRoom.setCapacite(updateRoom.getCapacite());
            roomService.saveRoom(existingRoom);


        } else {
            throw new RoomException("Room id not exist  : "+id);
        }
        return ResponseEntity.ok(null);
    }
}
