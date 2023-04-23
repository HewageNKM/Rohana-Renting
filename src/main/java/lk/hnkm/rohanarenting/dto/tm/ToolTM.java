/*
 * Copyright (c) $originalComment.match("Copyright \(c\) (\d+)", 1, "-", "$today.year")2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/5/23, 10:56 AM
 *
 */

package lk.hnkm.rohanarenting.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToolTM {
 private String tID;
 private String brandName;
 private String name;
 private String description;
 private String availability;
 private Double rate;
 private JFXButton editBtn;
 private JFXButton deleteBtn;
}
