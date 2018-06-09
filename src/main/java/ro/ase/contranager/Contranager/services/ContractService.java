package ro.ase.contranager.Contranager.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ase.contranager.Contranager.entities.Contract;
import ro.ase.contranager.Contranager.entities.ContractType;
import ro.ase.contranager.Contranager.entities.Partner;
import ro.ase.contranager.Contranager.pojos.ContractPojo;
import ro.ase.contranager.Contranager.repositories.ContractRepo;
import ro.ase.contranager.Contranager.repositories.ContractTypeRepo;
import ro.ase.contranager.Contranager.repositories.PartnerRepo;

@Service
public class ContractService {

  @Autowired
  ContractRepo contractRepo;
  @Autowired
  PartnerRepo partnerRepo;
  @Autowired
  ContractTypeRepo contractTypeRepo;

  public Contract addContract(ContractPojo contractPojo){

    Partner partner=partnerRepo.findByName(contractPojo.getPartnerName());
    ContractType contractType=contractTypeRepo.findByName(contractPojo.getContractType());
    boolean isCompleted=contractPojo.getEndDate().before(new Timestamp(System.currentTimeMillis()));

    Contract contract=new Contract(contractPojo.getNoContract(),contractPojo.getSubject(),
        contractPojo.getStartDate(),contractPojo.getEndDate(),contractPojo.getValue(),contractPojo.getCurrency(),
        isCompleted, contractPojo.getDocument(),contractType,partner,null);

    return contractRepo.save(contract);
  }

  public List<ContractPojo> displayAll(){
    List<Contract> contracts=contractRepo.findContractsOrdered();
    List<ContractPojo> contractPojos=new ArrayList<>();
    for (Contract contract:contracts
    ) {
        ContractPojo contractPojo=new ContractPojo();
        contractPojo.setNoContract(contract.getNoContract());
        contractPojo.setSubject(contract.getSubject());
        contractPojo.setValue(contract.getValue());
        contractPojo.setCurrency(contract.getCurrency());
        contractPojo.setStartDate(contract.getStartDate());
        contractPojo.setEndDate(contract.getEndDate());
        contractPojo.setContractType(contract.getContractType().getName());
        contractPojo.setPartnerName(contract.getPartner().getName());
        contractPojo.setDocument(contract.getDocument());
        contractPojos.add(contractPojo);
    }
    return contractPojos;
  }

  public Contract deleteContract(Long noContract){
    Contract contract= contractRepo.findByNoContract(noContract);
    if(contract!=null) {
      contractRepo.delete(contract);
      return contract;
    }
    else
      return null;
  }
}
