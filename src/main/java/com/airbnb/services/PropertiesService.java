package com.airbnb.services;

import com.airbnb.dto.PropertiesDto;
import com.airbnb.entities.Property;
import com.airbnb.entities.User;

import java.util.List;
import java.util.Properties;

public interface PropertiesService {
    void create(PropertiesDto propertiesDto , String userName);
    List<Property> getAllProperties();
    Property getPropertyById(Long id);
    void update(PropertiesDto propertiesDto , Long id,String userName);
    void delete(Long id,String userName);
    List<Property> getAllPropertiesByUser(Long userId);

}
