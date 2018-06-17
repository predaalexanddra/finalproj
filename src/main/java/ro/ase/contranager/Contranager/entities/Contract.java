package ro.ase.contranager.Contranager.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Entity
@Data
public class Contract {

    @Id
    private Long noContract;
    @NotNull
    @Size(min=2, max=30)
    private String subject;
    @NotNull
    private Timestamp startDate;
    @NotNull
    private Timestamp endDate;
    @NotNull
    private Double value;
    @NotNull
    private Currency currency;
    @NotNull
    private boolean isCompleted;
    private String document;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="contract_type_id")
    private ContractType contractType;
    @ManyToOne
    @JoinColumn(name="partner_cui")
    @JsonManagedReference
    private Partner partner;
    @JsonBackReference
    @OneToMany(mappedBy = "contract")
    private List<Payment> payments;

    public Contract(Long noContract,
        @NotNull @Size(min = 2, max = 30) String subject,
        @NotNull Timestamp startDate, @NotNull Timestamp endDate,
        @NotNull Double value,
        @NotNull Currency currency, @NotNull boolean isCompleted, String document,
        ContractType contractType, Partner partner,
        List<Payment> payments) {
        this.noContract = noContract;
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
        this.value = value;
        this.currency = currency;
        this.isCompleted = isCompleted;
        this.document = document;
        this.contractType = contractType;
        this.partner = partner;
        this.payments = payments;
    }

    public Contract() {
    }
}
