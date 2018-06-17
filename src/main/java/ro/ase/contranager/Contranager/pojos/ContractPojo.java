package ro.ase.contranager.Contranager.pojos;

import java.sql.Blob;
import java.time.LocalDate;
import lombok.Data;
import ro.ase.contranager.Contranager.entities.Currency;

@Data
public class ContractPojo {

  private Long noContract;
  private String subject;
  private LocalDate startDate;
  private LocalDate endDate;
  private Double value;
  private Currency currency;
  private String document;
  private String contractType;
  private String partnerName;

}
