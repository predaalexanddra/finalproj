package ro.ase.contranager.Contranager.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Address {
    @Id
    private String postalCode;
    @NotNull
    private String country;
    @NotNull
    private String city;
    @NotNull
    private String street;
    @OneToOne(mappedBy = "address")
    @JsonBackReference
    private Partner partner;
}
