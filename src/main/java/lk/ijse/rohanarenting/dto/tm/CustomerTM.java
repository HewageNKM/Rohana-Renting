/**
 * Created by Nadun Kawishika
 * date :
 * time :
 * project name :IntelliJ IDEA
 */
package lk.ijse.rohanarenting.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@Getter
public class CustomerTM {
    private String CID;
    private String firstName;
    private String lastName;
    private String NIC;
    private LocalDate birthday;
    private String mobileNumber;
    private String email;
    private String street;
    private String city;
    private Integer zipCode;
    private JFXButton editBtn;
    private JFXButton deleteBtn;
    private JFXButton showBtn;
}
