package com.airbnb.dto;


import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PropertiesDto {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Price per night is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per night must be greater than 0")
    private BigDecimal pricePerNight;

    @Min(value = 1, message = "There must be at least one bedroom")
    private Integer numberOfBedrooms;

    @Min(value = 1, message = "There must be at least one bathroom")
    private Integer numberOfBathrooms;

    private Boolean isAvailable;

    private Boolean drinkAllowed;

    private Boolean petAllowed;

    @Min(value = 1, message = "Maximum checkout time in nights must be at least 1")
    private int maxCheckoutTimeInNights;

    @DecimalMin(value = "0.0", message = "Extra charges must be greater than or equal to 0")
    private BigDecimal extraCharges;
}
