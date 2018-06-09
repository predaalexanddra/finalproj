package ro.ase.contranager.Contranager.pojos;

import java.sql.Blob;
import java.sql.Timestamp;
import lombok.Data;
import ro.ase.contranager.Contranager.entities.Currency;

@Data
public class ContractPojo {

  private Long noContract;
  private String subject;
  private Timestamp startDate;
  private Timestamp endDate;
  private Double value;
  private Currency currency;
  private Blob document;
  private String contractType;
  private String partnerName;

}
