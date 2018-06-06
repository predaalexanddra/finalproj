package ro.ase.contranager.Contranager.entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @NotNull
  private String name;

  @OneToMany(mappedBy = "department")
  private List<Employee> employees;
}
