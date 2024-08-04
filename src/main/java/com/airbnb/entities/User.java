package com.airbnb.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String fullName;

    private String phoneNumber;

    @OneToMany(mappedBy = "owner")
    @JsonManagedReference
    private List<Property> properties;

//    @OneToMany(mappedBy = "user")
//    private List<Review> reviews;
}

