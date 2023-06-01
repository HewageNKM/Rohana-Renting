/*
 * Copyright (c) 2023, All right reserved.
 * Author: Nadun Kawishika
 * Project Name: RohanaRenting
 * Date and Time: 4/2/23, 3:25 PM
 *
 */

package lk.hnkm.rohanarenting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class VehicleDTO {
  private String VID;
  private String manufacturer;
  private String modelName;
  private String description;
  private String Availability;
  private Double rate;
  private String category;
}
