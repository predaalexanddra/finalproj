package ro.ase.contranager.Contranager.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class ContractType
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min=2, max=30)
    private String name;
    @OneToMany(mappedBy = "contractType")
    private List<Contract> contracts;

}
