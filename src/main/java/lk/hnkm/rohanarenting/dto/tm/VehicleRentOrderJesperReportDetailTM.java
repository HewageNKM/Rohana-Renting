/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/23/23, 8:02 PM
 *
 */

package lk.hnkm.rohanarenting.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class VehicleRentOrderJesperReportDetailTM {
    private String vehicleID;
    private String vehicleManufacture;
    private String vehicleModelName;
    private String category;
    private Integer rentDays;
    private Double rate;
    private Double total;
    private Date returnDate;
}
