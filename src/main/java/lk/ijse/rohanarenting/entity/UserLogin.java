package lk.ijse.rohanarenting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserLogin {
    private String employeeId;
    private LocalDate date;
    private LocalTime logTime;
    private LocalTime logOutTime;
}
