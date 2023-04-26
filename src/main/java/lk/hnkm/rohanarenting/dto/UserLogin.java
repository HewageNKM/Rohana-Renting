/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/27/23, 11:41 AM
 *
 */

package lk.hnkm.rohanarenting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserLogin {
    private String EID;
    private String userName;
    private LocalDate date;
    private LocalTime logTime;
    private LocalTime logoutTime;

}
