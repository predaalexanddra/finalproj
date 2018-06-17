package ro.ase.contranager.Contranager.services;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import javax.persistence.criteria.CriteriaBuilder.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ro.ase.contranager.Contranager.entities.Contract;
import ro.ase.contranager.Contranager.entities.ContractType;
import ro.ase.contranager.Contranager.entities.Currency;
import ro.ase.contranager.Contranager.entities.Partner;
import ro.ase.contranager.Contranager.pojos.Company;
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
  @Autowired
  MailSender mailSender;

  public Contract addContract(ContractPojo contractPojo){

    Partner partner=partnerRepo.findByName(contractPojo.getPartnerName());
    ContractType contractType=contractTypeRepo.findByName(contractPojo.getContractType());
    boolean isCompleted=contractPojo.getEndDate().isBefore(LocalDate.now());

    Contract contract=new Contract(contractPojo.getNoContract(),contractPojo.getSubject(),
       Timestamp.valueOf( contractPojo.getStartDate().atStartOfDay()),Timestamp.valueOf(contractPojo.getEndDate().atStartOfDay()),contractPojo.getValue(),contractPojo.getCurrency(),
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
        contractPojo.setStartDate(contract.getStartDate().toLocalDateTime().toLocalDate());
        contractPojo.setEndDate(contract.getEndDate().toLocalDateTime().toLocalDate());
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
      String email=contract.getPartner().getContact().getEmail();
      String body="The contract no. "+contract.getNoContract()+", "+contract.getSubject()+
          " concluded with our company on "+ contract.getStartDate().toLocalDateTime().toLocalDate()
          + " has been deleted from our database. If this is a mistake, let us know."
          + "\n"
          + "This is an automatically sent mail, do not respond to it.\n"
          + "\n"
          + "With consideration,\n "
          + Company.getInstance().getName();
      mailSender.sendMail(Company.getInstance().getEmail(),email,"Contract", body);
      return contract;
    }
    else
      return null;
  }

  @Scheduled(fixedRate = 86400000 )
  public void updateIsCompleted(){
    List<Contract> contracts= contractRepo.findContractsByIsCompletedIsFalse();
    for (Contract contract:contracts
    ) {
      if(contract.getEndDate().before(new Timestamp(System.currentTimeMillis()))){
        contract.setCompleted(true);
      }
    }
  }

  public String countContracts(){
    List<Contract> contracts=contractRepo.findContractsOrderedByYear();
    List<Integer> years=new ArrayList<>();
    int counts[]=new int[10];
    for (Contract contract :contracts) {
      int year = contract.getStartDate().toLocalDateTime().getYear();
      if (!years.contains(year)) {
        years.add(year);
      }
      counts[years.indexOf(year)]++;
    }
    int[] newArray = Arrays.copyOfRange(counts, 0, 3);
    return "{\"years\":"+years.subList(0,3)+",\"counts\":["+newArray[0]+","+
        +newArray[1]+","+newArray[2]+"]}";
  }

  public String longtermContracts(){
    List<Contract> contracts=contractRepo.findAll();
    List<Integer> durations=new ArrayList<>();
    for (Contract contract:contracts) {
      int duration=contract.getEndDate().toLocalDateTime().getYear()
          -contract.getStartDate().toLocalDateTime().getYear();
      durations.add(duration);
    }
    durations.sort(new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        return o2.compareTo(o1);
      }
    });
    List<String > partners=new ArrayList<>();
    for(int i=0;i<3;i++) {
      for (Contract contract : contracts) {
        int duration=contract.getEndDate().toLocalDateTime().getYear()
            -contract.getStartDate().toLocalDateTime().getYear();
        if(duration==durations.get(i) && !partners.contains(contract.getPartner().getName())) {
          partners.add(contract.getPartner().getName());
        }
      }
    }
    return "{\"partners\":[\""+partners.get(0).split(" ")[0]+"\",\""+partners.get(1).split(" ")[0]+
        "\",\""+ partners.get(2).split(" ")[0]+"\"],\"durations\":"+durations.subList(0,3)+"}";

  }

  public String mostValuableContracts(){
    List<Contract> contracts=contractRepo.findContractsValues();
    List<Double>values=new ArrayList<>();
    List<Long> contractNumbers=new ArrayList<>();
    for (Contract contract:contracts) {
      if(contract.getCurrency()==Currency.DOLLAR)
        values.add(contract.getValue()*3.95);
      else
        if(contract.getCurrency()==Currency.EURO)
          values.add(contract.getValue()*4.66);
          else
            values.add(contract.getValue());
          contractNumbers.add(contract.getNoContract());
    }

    return "{\"contracts\":"+contractNumbers.subList(0,3)+",\"values\":"+values.subList(0,3)+"}";
  }
}
