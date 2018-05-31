package ro.ase.contranager.Contranager.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Address {
    @Id
    private Long postalCode;
    @NotNull
    private String country;
    @NotNull
    private String city;
    @NotNull
    private String street;
    @OneToOne(mappedBy = "address")
    private Partner partner;
}
