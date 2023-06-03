package lk.ijse.rohanarenting.entity;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String nIC;
    private String gender;
    private LocalDate birthday;
    private String mobileNumber;
    private String email;
    private Integer zipCode;
    private String city;
    private String street;
    private String state;
    private LocalDate joinedDate;
    private String position;
}
