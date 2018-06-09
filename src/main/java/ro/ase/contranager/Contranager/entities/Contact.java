package ro.ase.contranager.Contranager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
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
    @JsonIgnore
    private Partner partner;

    public Contact(Long id,
        @NotNull @Size(min = 2, max = 30) String name,
        @NotNull String email,
        @NotNull @Size(min = 10, max = 30) String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Contact() {
    }
}
