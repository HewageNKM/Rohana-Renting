package lk.ijse.rohanarenting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Insurance {
    private String insuranceID;
    private String insuranceName;
    private String insuranceProvider;
    private String agentName;
    private String agentContact;
    private String email;
    private String address;
    private String fax;
    private LocalDate joinedDate;
    private LocalDate expireDate;
}
