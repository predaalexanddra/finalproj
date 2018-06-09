package ro.ase.contranager.Contranager.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ase.contranager.Contranager.entities.Address;
import ro.ase.contranager.Contranager.entities.Contact;
import ro.ase.contranager.Contranager.entities.Partner;
import ro.ase.contranager.Contranager.pojos.PartnerPojo;
import ro.ase.contranager.Contranager.pojos.PartnerPojoSimplified;
import ro.ase.contranager.Contranager.repositories.AddressRepo;
import ro.ase.contranager.Contranager.repositories.ContactRepo;
import ro.ase.contranager.Contranager.repositories.PartnerRepo;

@Service
public class PartnerService {

  @Autowired
  private PartnerRepo partnerRepo;
  @Autowired
  private ContactRepo contactRepo;
  @Autowired
  private AddressRepo addressRepo;

  public Partner addPartner(PartnerPojo partnerPojo){
    List<Contact> contacts=contactRepo.findAll();
    Long id= contacts.get(contacts.size()-1).getId()+1;
    Contact contact=new Contact(id, partnerPojo.getContactName(),partnerPojo.getEmail(),partnerPojo.getPhone());
    Address address=new Address(partnerPojo.getPostalCode(),partnerPojo.getCountry(),partnerPojo.getCity(),partnerPojo.getStreet());

    addressRepo.save(address);
    contactRepo.save(contact);

    Partner partner=new Partner(partnerPojo.getCui(),partnerPojo.getName(),address,contact);

    return partnerRepo.save(partner);
  }

  public Partner deletePartner(Long cui){
    Partner partner= partnerRepo.findByCui(cui);
    if(partner!=null) {
      partnerRepo.delete(partner);
      return partner;
    }
    else
      return null;
  }

  public List<PartnerPojoSimplified> displayAll(){
    List<Partner> partners=partnerRepo.findAll();
    List<PartnerPojoSimplified> pojos=new ArrayList<>();
    for (Partner partner:partners
    ) {
      PartnerPojoSimplified pojo=new PartnerPojoSimplified();
      pojo.setCui(partner.getCui());
      pojo.setName(partner.getName());
      pojo.setCity(partner.getAddress().getCity());
      pojo.setContactName(partner.getContact().getName());
      pojo.setEmail(partner.getContact().getEmail());
      pojo.setPhone(partner.getContact().getPhone());
      pojos.add(pojo);
    }
    return pojos;
  }
}
