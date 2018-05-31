package ro.ase.contranager.Contranager.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Contact {
    @Id
    private Long id;
    @NotNull
    @Size(min=2, max=30)
    private String name;
    @NotNull
    private String email;
    @NotNull
    @Size(min=10, max=30)
    private String phone;
    @OneToOne(mappedBy = "contact")
    private Partner partner;
}
