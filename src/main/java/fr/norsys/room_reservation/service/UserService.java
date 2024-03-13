package fr.norsys.room_reservation.service;


import fr.norsys.room_reservation.entity.User;
import fr.norsys.room_reservation.exception.UserException;
import fr.norsys.room_reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    UserRepository userRepository ;
    
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return  userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long id) {
        Optional<User> user = userRepository.findById(id);;
        if (user.isEmpty()){
            throw new UserException("User id not exist  : "+id);
        }
        return user;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void DeleteUser(long id) {
        Optional<User> userDeleted = userRepository.findById(id);

        if (userDeleted.isEmpty()) {
            throw new UserException("User id not exist  : "+id);
        }
        userRepository.delete(userDeleted.get());
    }
    
    
}
