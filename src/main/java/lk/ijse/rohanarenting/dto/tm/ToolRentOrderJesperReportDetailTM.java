/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/23/23, 8:02 PM
 *
 */

package lk.ijse.rohanarenting.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ToolRentOrderJesperReportDetailTM {
    private String toolId;
    private String brandName;
    private String toolName;
    private Integer rentDays;
    private Double rate;
    private Double total;
    private Date returnDate;
}
