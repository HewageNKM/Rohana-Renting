package lk.ijse.rohanarenting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReturnDTO {
    private String returnId;
    private String rentId;
    private String returnDate;
    private String returnTime;
}
