package fr.norsys.room_reservation.controller;


import fr.norsys.room_reservation.entity.User;
import fr.norsys.room_reservation.exception.UserException;
import fr.norsys.room_reservation.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService ;

    @PostMapping
    public ResponseEntity<String> CreateUser(@RequestBody @Valid User user){
        Optional<User> userByEmail = userService.getUserByEmail(user.getEmail());

        if (userByEmail.isPresent()) {
            throw new UserException("Email already exist (email should be unique)");
        }

        userService.saveUser(user);
        return ResponseEntity.ok("User created successfully");
    }
    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUsers();

    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable int  id){

        return ResponseEntity.ok(userService.getUserById(id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        userService.DeleteUser(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable int id, @RequestBody @Valid User updateUser) throws Exception {
        

        Optional<User> existingUserOptional = userService.getUserById(id);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            existingUser.setName(updateUser.getName());
            existingUser.setEmail(updateUser.getEmail());

            userService.saveUser(existingUser);


        } else {
            throw new UserException("User id not exist  : "+id);
        }
        return ResponseEntity.ok(null);
    }
}
