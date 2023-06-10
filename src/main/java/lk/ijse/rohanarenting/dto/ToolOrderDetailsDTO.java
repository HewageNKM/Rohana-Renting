package lk.ijse.rohanarenting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ToolOrderDetailsDTO {
    private String rentId;
    private String toolId;
    private String brand;
    private String name;
    private Integer rentDays;
    private Double rate;
    private Double total;
    private LocalDate returnDate;
    private int returnStatus;
    private int refundStatus;
}
