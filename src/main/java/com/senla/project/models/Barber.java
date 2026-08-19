package com.senla.project.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "barbers")
@PrimaryKeyJoinColumn(name = "id")
public class Barber extends User {
    @Column(nullable = false)
    private Double rating;

    @Column(nullable = false, length = 100)
    private String specialty;

    @OneToMany(mappedBy = "barber")
    private List<Schedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "barber")
    private List<Appointment> appointments = new ArrayList<>();

    public Barber() {}

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
