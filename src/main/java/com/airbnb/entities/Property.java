package com.airbnb.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String address;
    private BigDecimal pricePerNight;
    private Integer numberOfBedrooms;
    private Integer numberOfBathrooms;
    private Boolean isAvailable;
    private Boolean drinkAllowed;
    private Boolean petAllowed;
    private Integer maxCheckoutTimeInNights;
    private BigDecimal extraCharges;

//    @OneToMany(mappedBy = "property")
//    private List<Review> reviews;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "owner_id")
    private User owner;
}
