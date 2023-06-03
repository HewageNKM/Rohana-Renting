package lk.ijse.rohanarenting.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class User {
   private String employeeId;
   private String username;
   private String password;
   private String permissionLevel;
}
