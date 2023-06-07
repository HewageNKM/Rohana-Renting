package lk.ijse.rohanarenting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Tool {
    private String toolID;
    private String brandName;
    private String toolName;
    private String description;
    private String availability;
    private Double rate;
}
