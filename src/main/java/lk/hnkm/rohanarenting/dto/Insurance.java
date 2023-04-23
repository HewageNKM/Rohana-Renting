/*
 * Copyright (c) $originalComment.match("Copyright \(c\) (\d+)", 1, "-", "$today.year")2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/5/23, 6:09 PM
 *
 */

package lk.hnkm.rohanarenting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Insurance {
    private String IID;
    private String name;
    private String insuranceProvider;
    private String agentName;
    private String agentContact;
    private String email;
    private String address;
    private String fax;
    private LocalDate joinedDate;
    private LocalDate expireDate;

}
