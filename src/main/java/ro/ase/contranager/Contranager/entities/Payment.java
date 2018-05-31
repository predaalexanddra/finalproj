package ro.ase.contranager.Contranager.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Blob;
import java.sql.Timestamp;

@Entity
public class Payment {

    @Id
    private Long noPayment;
    private Timestamp date;
    private Double value;
    private Blob document;
    @ManyToOne
    @JoinColumn(name="contract_no_contract")
    private Contract contract;

}
