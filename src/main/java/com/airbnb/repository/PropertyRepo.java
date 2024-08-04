package com.airbnb.repository;

import com.airbnb.entities.Property;
import com.airbnb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepo extends JpaRepository<Property,Long> {
    List<Property> findByOwner(User owner);
}
