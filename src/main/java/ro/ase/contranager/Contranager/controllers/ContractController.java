package ro.ase.contranager.Contranager.controllers;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.ase.contranager.Contranager.entities.Contract;
import ro.ase.contranager.Contranager.entities.Employee;
import ro.ase.contranager.Contranager.pojos.ContractPojo;
import ro.ase.contranager.Contranager.repositories.ContractRepo;
import ro.ase.contranager.Contranager.repositories.ContractTypeRepo;
import ro.ase.contranager.Contranager.services.ContractService;

@RestController
@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*")
public class ContractController {

  @Autowired
  ContractRepo contractRepo;

  @Autowired
  ContractTypeRepo contractTypeRepo;

  @Autowired
  ContractService contractService;

  @GetMapping(value = "/contracts")
  public List<Contract> findaAll() {
    return contractRepo.findAll();
  }

  @GetMapping(value = "/ordered")
  public List<ContractPojo> findAllContractsOrdered() {
    return contractService.displayAll();
  }

  @GetMapping(value = "/types")
  public List<String> findContractTypes() {
    return contractTypeRepo.findContractsTypenames();
  }

  @PostMapping(value = "/addcontract")
  public ResponseEntity<String> addNewContract(@RequestBody ContractPojo contractPojo) {
    Contract result = contractService.addContract(contractPojo);
    if (result != null) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(value = "/deletecontract")
  public ResponseEntity<String> deleteContract(@RequestParam(name = "no") Long no) {
    Contract result = contractService.deleteContract(no);
    if (result != null) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(value = "/chart1")
  public String findContractsValues() {
    return contractService.mostValuableContracts();
  }
  @GetMapping(value = "/chart2")
  public String findContractsPerYear() {
    return contractService.countContracts();
  }
  @GetMapping(value = "/chart3")
  public String findLongTermContracts() {
    return contractService.longtermContracts();
  }

  @PostMapping(value = "/getData")
  public ResponseEntity<String> findContractsByPartner(@RequestBody String partner) {
    StringBuilder result=contractService.partnerData(partner);
    System.out.println(result);
    if (result != null) {
      return new ResponseEntity<>(result.toString(),HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(value = "/getbyyear")
  public ResponseEntity<String> findContractsByYear(@RequestBody String year) {
    String result=contractService.contractsPerYear(Integer.parseInt(year));
    System.out.println(result);
    if (result != null) {
      return new ResponseEntity<>(result,HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(value="/getyears")
  public Set<Integer> getYears(){
    return contractService.getContractsYears();
  }
}
