package ro.ase.contranager.Contranager.services;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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

  public Contract addContract(ContractPojo contractPojo) {

    Partner partner = partnerRepo.findByName(contractPojo.getPartnerName());
    ContractType contractType = contractTypeRepo.findByName(contractPojo.getContractType());
    boolean isCompleted = contractPojo.getEndDate().isBefore(LocalDate.now());
    double ronValue = contractPojo.getValue();
    if (contractPojo.getCurrency().equals(Currency.DOLLAR)) {
      ronValue = contractPojo.getValue() * 3.95;
    } else if (contractPojo.getCurrency().equals(Currency.EURO)) {
      ronValue = contractPojo.getValue() * 4.66;
    }

    Contract contract = new Contract(contractPojo.getNoContract(), contractPojo.getSubject(),
        Timestamp.valueOf(contractPojo.getStartDate().atStartOfDay()),
        Timestamp.valueOf(contractPojo.getEndDate().atStartOfDay()), contractPojo.getValue(),
        contractPojo.getCurrency(),
        isCompleted, contractPojo.getDocument(), contractType, partner, null);
    contract.setRONvalue(ronValue);
    return contractRepo.save(contract);
  }

  public List<ContractPojo> displayAll() {
    List<Contract> contracts = contractRepo.findContractsOrdered();
    List<ContractPojo> contractPojos = new ArrayList<>();
    for (Contract contract : contracts
        ) {
      ContractPojo contractPojo = new ContractPojo();
      contractPojo.setNoContract(contract.getNoContract());
      contractPojo.setSubject(contract.getSubject());
      contractPojo.setValue(contract.getValue());
      contractPojo.setCurrency(contract.getCurrency());
      contractPojo.setStartDate(contract.getStartDate().toLocalDateTime().toLocalDate());
      contractPojo.setEndDate(contract.getEndDate().toLocalDateTime().toLocalDate());
      contractPojo.setContractType(contract.getContractType().getName());
      contractPojo.setPartnerName(contract.getPartner().getName());
      contractPojo.setDocument(contract.getDocument());
      contractPojo.setStatus(contract.isCompleted() ? "Inactive" : "Active");
      contractPojos.add(contractPojo);
    }
    return contractPojos;
  }

  public Contract deleteContract(Long noContract) {
    Contract contract = contractRepo.findByNoContract(noContract);
    if (contract != null) {
      contractRepo.delete(contract);
      String body = "Contractul cu numarul " + contract.getNoContract() + ", " + contract.getSubject() +
          " incheiat cu compania noastra la data de " + contract.getStartDate().toLocalDateTime()
          .toLocalDate()
          + " a fost arhivat ca urmare a neprelungirii acestuia."
          + "\n"
          + "Acesta este un mesaj trimis automat, nu raspundeti la el.\n"
          + "\n"
          + "Cu stima,\n "
          + Company.getInstance().getName();
      String email = contract.getPartner().getContact().getEmail();
      mailSender.sendMail(Company.getInstance().getEmail(), email, "Contract", body);
      return contract;
    } else {
      return null;
    }
  }

  @Scheduled(fixedRate = 86400000)
  public void updateIsCompleted() {
    List<Contract> contracts = contractRepo.findContractsByIsCompletedIsFalse();
    for (Contract contract : contracts) {
      if (contract.getEndDate().before(new Timestamp(System.currentTimeMillis()))) {
        contract.setCompleted(true);
      }
    }
  }

  public String countContracts() {
    List<Contract> contracts = contractRepo.findContractsOrderedByYear();
    List<Integer> years = new ArrayList<>();
    int counts[] = new int[10];
    for (Contract contract : contracts) {
      int year = contract.getStartDate().toLocalDateTime().getYear();
      if (!years.contains(year)) {
        years.add(year);
      }
      counts[years.indexOf(year)]++;
    }
    int[] newArray = Arrays.copyOfRange(counts, 0, 3);
    return "{\"years\":" + years.subList(0, 3) + ",\"counts\":[" + newArray[0] + "," +
        +newArray[1] + "," + newArray[2] + "]}";
  }

  public String longtermContracts() {
    List<Contract> contracts = contractRepo.findAll();
    List<Integer> durations = new ArrayList<>();
    for (Contract contract : contracts) {
      int duration = contract.getEndDate().toLocalDateTime().getYear()
          - contract.getStartDate().toLocalDateTime().getYear();
      durations.add(duration);
    }
    durations.sort(new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        return o2.compareTo(o1);
      }
    });
    List<String> partners = new ArrayList<>();
    for (int i = 0; i <= 4; i++) {
      for (Contract contract : contracts) {
        int duration = contract.getEndDate().toLocalDateTime().getYear()
            - contract.getStartDate().toLocalDateTime().getYear();
        if (duration == durations.get(i) && !partners.contains(contract.getPartner().getName())) {
          partners.add(contract.getPartner().getName());
        }
      }
    }
    return "{\"partners\":[\"" + partners.get(0).split(" ")[0].substring(0, 5) + "\",\"" +
        partners.get(1).split(" ")[0].substring(0, 5) +
        "\",\"" + partners.get(2).split(" ")[0].substring(0, 5) +
        "\",\"" + partners.get(3).split(" ")[0].substring(0, 5) +
        "\",\"" + partners.get(4).split(" ")[0].substring(0, 4) + "\"],\"durations\":" + durations
        .subList(0, 5) + "}";

  }

  public String mostValuableContracts() {
    List<Contract> contracts = contractRepo.findContractsValues();
    List<Double> values = new ArrayList<>();
    List<Long> contractNumbers = new ArrayList<>();
    for (Contract contract : contracts) {
      if (contract.getCurrency() == Currency.DOLLAR) {
        values.add(contract.getValue() * 3.95);
      } else if (contract.getCurrency() == Currency.EURO) {
        values.add(contract.getValue() * 4.66);
      } else {
        values.add(contract.getValue());
      }
      contractNumbers.add(contract.getNoContract());
    }
    return "{\"contracts\":" + contractNumbers.subList(0, 5) + ",\"values\":" + values.subList(0, 5)
        + "}";
  }

  public StringBuilder partnerData(String partnerName) {
    Partner partner = partnerRepo.findByName(partnerName);
    List<Contract> contracts = contractRepo.findByPartnerOrderByStartDate(partner);
    StringBuilder json = new StringBuilder();
    json.append("{ \"years\":[");
    for (Contract contract : contracts) {
      json.append(contract.getStartDate().toLocalDateTime().getYear());
      json.append(",");
    }
    json.deleteCharAt(json.length() - 1);
    json.append("], \"values\":[");
    for (Contract contract : contracts) {
      json.append(contract.getValue());
      json.append(",");
    }
    json.deleteCharAt(json.length() - 1);
    json.append("]}");
    return json;
  }

  public String contractsPerYear(int year){
    List<Contract> allContracts= contractRepo.findContractsOrderedByYear();
    List<Contract> contractsPerYear=new ArrayList<>();
    for (Contract contract:allContracts) {
      if(contract.getStartDate().toLocalDateTime().getYear()==year)
        contractsPerYear.add(contract);
    }

    StringBuilder json=new StringBuilder();
    json.append("{\"months\":[");
    for (Contract contract: contractsPerYear) {
      int month=contract.getStartDate().getMonth()+1;
      json.append(month).append(",");
    }
    json.deleteCharAt(json.length()-1);
    json.append("], \"values\":[");
    for (Contract contract: contractsPerYear) {
      json.append(contract.getValue()).append(",");
    }
    json.deleteCharAt(json.length()-1);
    json.append("]}");
    return json.toString();
  }

  public Set<Integer> getContractsYears(){
    List<Contract> contracts=contractRepo.findAll();
    Set<Integer> years=new TreeSet<>();

    for (Contract contract:contracts) {
        years.add(contract.getStartDate().toLocalDateTime().getYear());
    }
    return years;
  }
}
