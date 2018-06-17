package ro.ase.contranager.Contranager.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.ase.contranager.Contranager.entities.Partner;
import ro.ase.contranager.Contranager.pojos.PartnerPojo;
import ro.ase.contranager.Contranager.repositories.PartnerRepo;
import ro.ase.contranager.Contranager.services.PartnerService;

@RestController
@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*")
public class PartnerController {

  @Autowired
  PartnerRepo partnerRepo;
  @Autowired
  PartnerService partnerService;

  @GetMapping(value="/partners")
  public List<PartnerPojo> findAll(){
    return partnerService.displayAll();
  }

  @GetMapping(value="/partnersnames")
  public List<String> findPartnersNames(){
    return partnerRepo.findPartnersNames();
  }

  @PostMapping(value = "/addpartner")
  public ResponseEntity<? extends Object> addNewPartner(@RequestBody PartnerPojo partnerPojo){
    Partner result=partnerService.addPartner(partnerPojo);
    if(result!=null)
      return new ResponseEntity<>(HttpStatus.OK);
    else
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value="/deletepartner")
  public ResponseEntity<String> deletePartner(@RequestParam(name = "cui") Long cui){
    Partner result=partnerService.deletePartner(cui);
    if(result!=null)
      return new ResponseEntity<>(HttpStatus.OK);
    else
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }
}
