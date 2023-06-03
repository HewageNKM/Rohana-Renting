/*
 * Copyright (c) $originalComment.match("Copyright \(c\) (\d+)", 1, "-", "$today.year")2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/6/23, 9:06 PM
 *
 */

package lk.ijse.rohanarenting.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToolCartTM {
   private String rentOrderID;
   private String toolID;
   private String brandName;
   private String toolName;
   private String customerID;
   private String description;
   private Double rate;
   private Integer rentDays;
   private Double total;
   private JFXButton remove;
}
