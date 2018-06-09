package ro.ase.contranager.Contranager.pojos;

import lombok.Data;

@Data
public class PartnerPojo {

  private Long cui;
  private String name;
  private String postalCode;
  private String city;
  private String country;
  private String street;
  private String contactName;
  private String email;
  private String phone;

}
