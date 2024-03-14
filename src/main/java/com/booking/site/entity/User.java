package com.booking.site.entity;

import com.booking.site.jsonViews.Views;
import com.booking.site.serializer.UserBookingsSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.List;

@Entity
@Table(name = "user_info")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private int id;
    @JsonView(Views.userView.class)
    @Column(name = "fname")
    private String fname;
    @JsonView(Views.userView.class)
    @Column(name = "lname")
    private String lname;
    @JsonView(Views.adminView.class)
    @Column(name = "email")
    private String email;
    @JsonView(Views.adminView.class)
    @Column(name = "password")
    private String password;
    @JsonView(Views.adminView.class)
    @Column(name = "phno")
    private String phoneNumber;
    @JsonView(Views.adminView.class)
    @Column(name = "location")
    private String location;
    @JsonView(Views.userView.class)
    @Column(name = "pfp")
    private byte[] pfp;
    @JsonView(Views.adminView.class)
    @Column(name ="verified")
    private boolean verified;
    @JsonView(Views.adminView.class)
    @Column(name = "dob")
    private String dob;
    @JsonView(Views.adminView.class)
    @OneToMany(mappedBy = "user")
    private List<Wishlist> wishlists;
    @JsonView(Views.adminView.class)
    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;
    @JsonView(Views.adminView.class)
    @OneToMany(mappedBy = "user" ,fetch = FetchType.EAGER)
    private List<Authrorities> authrorities;

    @JsonView(Views.adminView.class)
    @OneToMany(mappedBy = "creator")
    private List<Event> createdEvents;

}
