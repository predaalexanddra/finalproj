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

  @Scheduled(fixedRate = 120000)
  public void sendEmails(){
    List<Contract> availableContracts= contractRepo.findContractsByIsCompletedIsFalse();
    LocalDate expireDate= LocalDate.now().plusMonths(1);
    for (Contract contract:availableContracts) {
      if(contract.getEndDate().toLocalDateTime().toLocalDate().equals(expireDate)){
        String email=contract.getPartner().getContact().getEmail();
        String body="The contract no. "+contract.getNoContract()+", "+contract.getSubject()+
            " concluded with our company on "+ contract.getStartDate().toLocalDateTime().toLocalDate()+
            ", will expire next month.\n" +"Please contact our agents to begin renegotiation.\n "+
            "\n"
            + "This is an automatically sent mail, do not respond to it.\n"
            + "\n"
            + "With consideration,\n "
            + Company.getInstance().getName();
        mailSender.sendMail(Company.getInstance().getEmail(),email,"Contract", body);
      }
    }
  }

}
