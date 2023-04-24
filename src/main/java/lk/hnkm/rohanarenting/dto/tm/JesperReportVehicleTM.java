/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/24/23, 7:41 PM
 *
 */

package lk.hnkm.rohanarenting.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class JesperReportVehicleTM {
    private String rentId;
    private String VID;
    private String orderDate;
}
