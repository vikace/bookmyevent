package com.booking.site.entity;

import com.booking.site.jsonViews.Views;
import com.booking.site.serializer.CreatorSerializer;
import com.booking.site.serializer.EventSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "event_info")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.userView.class)
    private int id;
    @JsonView(Views.userView.class)
    private String name,description,location,category,instagram,facebook,twitter,website,mode;
    @JsonView(Views.userView.class)
    @Column(name = "max_age")
    private int maxAge;
    @JsonView(Views.userView.class)
    private int occupied,capacity,views;
    @JsonView(Views.userView.class)
    @JsonSerialize(using = CreatorSerializer.class)
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    @JsonView(Views.userView.class)
    private boolean cancelled,expired;
    @JsonView(Views.userView.class)
    private String starts,ends;
    @Column(name = "cover_blob")
    @JsonView(Views.userView.class)
    private byte[] cover;
    @JsonView(Views.adminView.class)
    @OneToMany(mappedBy = "event")
    private List<Booking> bookings;
    @JsonView(Views.adminView.class)
    @OneToMany(mappedBy = "event")
    private List<Wishlist> wishlists;

}

