package ro.ase.contranager.Contranager.entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Employee {

  @Id
  private Long cnp;
  @NotNull
  private String name;
  @NotNull
  private String phone;
  @NotNull
  private boolean isAdmin;
  @NotNull
  private String username;
  @NotNull
  private String password;

  @ManyToOne
  @JoinColumn(name="fk_id_dep")
  private Department department;

  @OneToMany(mappedBy = "employee")
  private List<Log> logs;

  public Employee(Long cnp, @NotNull String name,
      @NotNull String phone, @NotNull boolean isAdmin,
      @NotNull String username, @NotNull String password,
      Department department) {
    this.cnp = cnp;
    this.name = name;
    this.phone = phone;
    this.isAdmin = isAdmin;
    this.username = username;
    this.password = password;
    this.department = department;
  }

  public Employee() {
  }
}

