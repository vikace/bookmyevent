package com.booking.site.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feature")
public class Feature {
    private byte[] cover;
    private String title;
    private String category;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
