/**
 * Created by Nadun Kawishika
 * date :
 * time :
 * project name :IntelliJ IDEA
 */
package lk.hnkm.rohanarenting.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTM {
 private String EID;
 private String firstName;
 private String lastName;
 private String NIC;
 private String gender;
 private LocalDate birthday;
 private String mobile;
 private String email;
 private Integer zipCode;
 private String city;
 private String street;
 private String state;
 private LocalDate joinedDate;
 private String position;
 private JFXButton showBtn;
 private JFXButton edit;
 private JFXButton delete;
}
