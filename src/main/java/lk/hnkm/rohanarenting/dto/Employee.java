/**
 * Created by Nadun Kawishika
 * date :
 * time :
 * project name :IntelliJ IDEA
 */
package lk.hnkm.rohanarenting.dto;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
  private  String EID;
  private String fistName;
  private String lastName;
  private String nic;
  private String gender;
  private LocalDate dateOfBirth;
  private String mobileNumber;
  private String email;
  private Integer zip;
  private String city;
  private String Street;
  private String state;
  private LocalDate joinedDate;
  private String position;
}
