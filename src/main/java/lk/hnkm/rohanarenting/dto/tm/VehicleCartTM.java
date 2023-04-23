/*
 * Copyright (c) $originalComment.match("Copyright \(c\) (\d+)", 1, "-", "$today.year")2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/6/23, 9:06 PM
 *
 */

package lk.hnkm.rohanarenting.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCartTM {
   private String rentOrderID;
   private String vehicleID;
   private String vehicleManufacture;
   private String vehicleModelName;
   private String customerID;
   private String description;
   private String category;
   private Double rate;
   private Integer rentDays;
   private Double total;
   private Integer returnStatus;
   private JFXButton remove;
}
