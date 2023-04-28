package lk.hnkm.rohanarenting.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginHistoryTM {
    private String id;
    private String date;
    private String logTime;
    private String logoutTime;
}
