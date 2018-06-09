package ro.ase.contranager.Contranager.pojos;

import lombok.Data;

@Data
public class EmployeePojoSimplified {

  private String name;
  private String phone;
  private String department;
  private String email;
  private boolean isAdmin;
}
