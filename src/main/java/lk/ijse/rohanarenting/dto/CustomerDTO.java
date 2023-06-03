/**
 * Created by Nadun Kawishika
 * date :
 * time :
 * project name :IntelliJ IDEA
 */
package lk.ijse.rohanarenting.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@Getter
public class CustomerDTO {
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
}
