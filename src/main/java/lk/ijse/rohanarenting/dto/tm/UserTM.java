/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/9/23, 8:15 PM
 *
 */

package lk.ijse.rohanarenting.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserTM {
    String EID;
    String name;
    String uPassword;
    String permissionLevel;
    JFXButton editBtn;
    JFXButton deleteBtn;
}
