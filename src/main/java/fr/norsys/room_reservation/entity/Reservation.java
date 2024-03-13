package fr.norsys.room_reservation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User id shouldn't be null")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @NotNull(message = "room id shouldn't be null")
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    @FutureOrPresent(message = "Start time must be in the present or future")
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    @Future(message = "End time must be in the future")
    private Date endTime;

    @JsonIgnore
    @AssertTrue(message = "End time must be after start time")
    private boolean isValidEndTime() {
        return endTime == null || endTime.after(startTime);
    }

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
