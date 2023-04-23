/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/22/23, 11:48 PM
 *
 */

package lk.hnkm.rohanarenting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class VehicleJesperReport {
    private String vehicleID;
    private String vehicleManufacture;
    private String vehicleModelName;
    private String category;
    private Integer rentDays;
    private Double rate;
    private Double total;
    private Date returnDate;
}
