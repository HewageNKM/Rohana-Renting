package lk.ijse.rohanarenting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RefundDetails {
    private String refundId;
    private String id;
    private Double total;
    private Double refundAmount;
}
