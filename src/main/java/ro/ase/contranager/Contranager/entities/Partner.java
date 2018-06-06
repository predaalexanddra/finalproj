package ro.ase.contranager.Contranager.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Entity
@Data
public class Partner {

    @Id
    private Long cui;
    @Size(min=2, max=30)
    private String name;
    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "address_postal_code")
    private Address address;
    @OneToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;
    @JsonBackReference
    @OneToMany(mappedBy = "partner")
    private List<Contract> contracts;

}
