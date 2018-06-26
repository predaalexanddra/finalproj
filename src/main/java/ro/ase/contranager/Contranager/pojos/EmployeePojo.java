package ro.ase.contranager.Contranager.pojos;

import lombok.Data;

@Data
public class EmployeePojo {

  private Long cnp;
  private String name;
  private String phone;
  private String department;
  private String email;
  private String password;
  private String isAdmin;

}
