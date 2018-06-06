package ro.ase.contranager.Contranager.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Log {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @NotNull
  private Date date;

  @ManyToOne
  @JoinColumn(name="fk_employee_cnp")
  @NotNull
  private Employee employee;
}
