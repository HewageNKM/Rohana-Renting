/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/14/23, 10:21 PM
 *
 */

package lk.hnkm.rohanarenting.dto.tm;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ReturnTM {
    private String returnId;
    private String productId;
    private LocalDate returnDate;
    private LocalDate returnedDate;
    private int lateDays;
    private Double fine;

}
