package lk.ijse.rohanarenting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Vehicle {
    private String vehicleID;
    private String manufacturer;
    private String model;
    private String description;
    private String availability;
    private Double rate;
    private String category;
}
