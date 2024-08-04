package com.airbnb.services.impl;

import com.airbnb.dto.PropertiesDto;
import com.airbnb.entities.Property;
import com.airbnb.entities.User;
import com.airbnb.exception.NotAllowedException;
import com.airbnb.exception.ResourceNotFoundExcaption;
import com.airbnb.repository.PropertyRepo;
import com.airbnb.repository.UserRepository;
import com.airbnb.services.PropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
public class PropertiesServiceImpl implements PropertiesService {

    private PropertyRepo propertyRepo;
    private UserRepository userRepository;

    public PropertiesServiceImpl(PropertyRepo propertyRepo, UserRepository userRepository) {
        this.propertyRepo = propertyRepo;
        this.userRepository = userRepository;
    }

    @Override
    public void create(PropertiesDto propertiesDto, String userName) {
        User byUserName = userRepository.findByUserName(userName);
        User getUser = userRepository.findById(byUserName.getId()).orElseThrow(() -> new ResourceNotFoundExcaption("user not found with id " + byUserName.getId()));

        Property build = Property.builder()
                .name(propertiesDto.getName())
                .description(propertiesDto.getDescription())
                .address(propertiesDto.getAddress())
                .pricePerNight(propertiesDto.getPricePerNight())
                .numberOfBedrooms(propertiesDto.getNumberOfBedrooms())
                .numberOfBathrooms(propertiesDto.getNumberOfBathrooms())
                .isAvailable(propertiesDto.getIsAvailable())
                .drinkAllowed(propertiesDto.getDrinkAllowed())
                .petAllowed(propertiesDto.getPetAllowed())
                .maxCheckoutTimeInNights(propertiesDto.getMaxCheckoutTimeInNights())
                .extraCharges(propertiesDto.getExtraCharges())
                .owner(getUser)
                .build();
        propertyRepo.save(build);

    }

    @Override
    public List<Property> getAllProperties() {
        return propertyRepo.findAll();
    }

    @Override
    public Property getPropertyById(Long id) {

        Property property = propertyRepo.findById(id).orElseThrow(() -> new ResourceNotFoundExcaption("property not found with id " + id));
        return property;
    }

    Logger logger = LoggerFactory.getLogger(PropertiesService.class);
    @Override
    public void update(PropertiesDto propertiesDto, Long id , String userName) {



        logger.info("isavalible {}",propertiesDto.getIsAvailable());
        Property property = propertyRepo.findById(id).orElseThrow(() -> new ResourceNotFoundExcaption("property not found with id " + id));
        User byUserName = userRepository.findByUserName(userName);
        if(property.getOwner().getId() != byUserName.getId())
            throw new NotAllowedException("You are not allow to update this property");


        property.setName(propertiesDto.getName());
        property.setDescription(propertiesDto.getDescription());
        property.setAddress(propertiesDto.getAddress());
        property.setPricePerNight(propertiesDto.getPricePerNight());
        property.setNumberOfBedrooms(propertiesDto.getNumberOfBedrooms());
        property.setNumberOfBathrooms(propertiesDto.getNumberOfBathrooms());
        property.setIsAvailable(propertiesDto.getIsAvailable());
        property.setDrinkAllowed(propertiesDto.getDrinkAllowed());
        property.setPetAllowed(propertiesDto.getPetAllowed());
        property.setMaxCheckoutTimeInNights(propertiesDto.getMaxCheckoutTimeInNights());
        property.setExtraCharges(propertiesDto.getExtraCharges());
        propertyRepo.save(property);
    }

    @Override
    public void delete(Long id,String userName) {
        User byUserName = userRepository.findByUserName(userName);
        Property property = propertyRepo.findById(id).orElseThrow(() -> new ResourceNotFoundExcaption("property not found with id " + id));

        if(byUserName.getId()!=property.getOwner().getId())
            throw new NotAllowedException("You are not allowed to delete this property");

        propertyRepo.delete(property);
    }

    @Override
    public List<Property> getAllPropertiesByUser(Long userId,String userName) {
        User byUserName = userRepository.findByUserName(userName);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundExcaption("User not found with id " + userId));

        if(byUserName.getId()!=user.getId())
            throw new NotAllowedException("You are not allowed to access properties");
        List<Property> byOwner = propertyRepo.findByOwner(user);
        return byOwner;
    }
}
