package lk.ijse.rohanarenting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReturnDetails {
    private String returnId;
    private String Id;
    private LocalDate returnDate;
    private LocalDate returnedDate;
    private Integer LateDays;
    private Double lateFee;
}
