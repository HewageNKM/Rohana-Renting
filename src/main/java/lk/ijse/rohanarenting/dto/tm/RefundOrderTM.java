/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/17/23, 7:56 AM
 *
 */

package lk.ijse.rohanarenting.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RefundOrderTM {
    private String productId;
    private Integer rentDays;
    private Double total;
    private LocalDate purchaseDate;
}
