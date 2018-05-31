package ro.ase.contranager.Contranager.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Partner {

    @Id
    private Long CUI;
    @Size(min=2, max=30)
    private String name;
    @OneToOne
    @JoinColumn(name = "address_postal_code")
    private Address address;
    @OneToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;
    @OneToMany(mappedBy = "partner")
    private List<Contract> contracts;

}
