/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/17/23, 10:15 AM
 *
 */

package lk.hnkm.rohanarenting.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RefundTM {
    private String productId;
    private Integer rentDays;
    private Double total;
    private Double refundAmount;
}
