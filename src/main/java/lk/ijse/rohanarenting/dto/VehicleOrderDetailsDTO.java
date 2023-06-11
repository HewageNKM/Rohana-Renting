package lk.ijse.rohanarenting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleOrderDetailsDTO {
    private String rentId;
    private String vehicleId;
    private String manufacturer;
    private String model;
    private String category;
    private Integer rentDays;
    private Double rate;
    private Double total;
    private LocalDate returnDate;
    private int returnStatus;
    private int refundStatus;
}
