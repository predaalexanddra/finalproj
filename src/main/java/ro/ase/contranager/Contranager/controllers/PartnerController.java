package ro.ase.contranager.Contranager.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ase.contranager.Contranager.entities.Partner;
import ro.ase.contranager.Contranager.repositories.PartnerRepo;

@RestController
public class PartnerController {

  @Autowired
  PartnerRepo partnerRepo;

  @GetMapping(value="/partners")
  public List<Partner> findAll(){
    return partnerRepo.findAll();
  }
}
