package ro.ase.contranager.Contranager.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Entity
@Data
public class ContractType
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min=2, max=30)
    private String name;
    @JsonBackReference
    @OneToMany(mappedBy = "contractType")
    private List<Contract> contracts;

}
