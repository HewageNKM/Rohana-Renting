/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/13/23, 10:55 PM
 *
 */

package lk.ijse.rohanarenting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class VehicleOrderDTO {
  private String rentalOrderId;
  private String customerID;
  private String vehicleId;
  private Integer rentDays;
}
