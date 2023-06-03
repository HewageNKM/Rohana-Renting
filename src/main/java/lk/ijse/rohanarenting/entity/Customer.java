package lk.ijse.rohanarenting.entity;

import lombok.*;

import java.time.LocalDate;
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String customerId;
    private String firstName;
    private String lastName;
    private String nic;
    private LocalDate birthday;
    private String mobileNumber;
    private String email;
    private String street;
    private String city;
    private Integer zipCode;
}
