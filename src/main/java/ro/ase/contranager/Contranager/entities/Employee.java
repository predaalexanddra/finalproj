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
  @GeneratedValue(strategy = GenerationType.AUTO)
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

  public Long getCnp() {
    return cnp;
  }

  public void setCnp(Long cnp) {
    this.cnp = cnp;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isAdmin() {
    return isAdmin;
  }
}

