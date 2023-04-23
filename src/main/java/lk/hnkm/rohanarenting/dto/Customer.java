/**
 * Created by Nadun Kawishika
 * date :
 * time :
 * project name :IntelliJ IDEA
 */
package lk.hnkm.rohanarenting.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@Getter
public class Customer {
    private String CID;
    private String fistName;
    private String lastName;
    private String NIC;
    private LocalDate birthday;
    private String mobileNumber;
    private String email;
    private String street;
    private String city;
    private Integer zipCode;
}
