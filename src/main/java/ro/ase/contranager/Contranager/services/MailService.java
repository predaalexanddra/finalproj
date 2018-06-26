package ro.ase.contranager.Contranager.services;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.ase.contranager.Contranager.entities.Contract;
import ro.ase.contranager.Contranager.entities.Partner;
import ro.ase.contranager.Contranager.pojos.Company;
import ro.ase.contranager.Contranager.repositories.ContractRepo;

@Component
public class MailService {

  @Autowired
  private ContractRepo contractRepo;

  @Autowired
  private MailSender mailSender;

  @Scheduled(fixedRate = 86400000)
  public void sendEmails(){
    List<Contract> availableContracts= contractRepo.findContractsByIsCompletedIsFalse();
    LocalDate expireDate= LocalDate.now().plusMonths(1);
    for (Contract contract:availableContracts) {
      if(contract.getEndDate().toLocalDateTime().toLocalDate().equals(expireDate)){
        String email=contract.getPartner().getContact().getEmail();
        String body="Contractul cu numarul "+contract.getNoContract()+", "+contract.getSubject()+
            ", incheiat cu compania noastra la data de "+ contract.getStartDate().toLocalDateTime().toLocalDate()+
            ", va expira intr-o luna.\n" +"Pentru renegocire, contactati agentii nostri.\n "+
            "\n"
            + "Acesta este un mesaj trimis automat, nu raspundeti la el.\n"
            + "\n"
            + "Cu stima,\n "
            + Company.getInstance().getName();
        mailSender.sendMail(Company.getInstance().getEmail(),email,"Contract", body);
      }
    }
  }

}
