package lk.hnkm.rohanarenting.dto.tm;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderTM {
    private String id;
    private String customerId;
    private LocalDate date;
    private LocalTime time;
    private String status;
}
