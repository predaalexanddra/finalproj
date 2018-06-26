package ro.ase.contranager.Contranager.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.ase.contranager.Contranager.entities.Contract;
import ro.ase.contranager.Contranager.entities.Partner;

public interface ContractRepo extends JpaRepository<Contract, Long> {

  @Query("select c from Contract c order by c.isCompleted,c.endDate asc ")
  List<Contract> findContractsOrdered();
  List<Contract> findContractsByIsCompletedIsFalse();
  Contract findByNoContract(Long no);

  @Query("select c from Contract c order by c.startDate asc ")
  List<Contract> findContractsOrderedByYear();

  @Query("select c from Contract c order by c.RONvalue desc ")
  List<Contract> findContractsValues();

  List<Contract> findByPartnerOrderByStartDate(Partner partner);

}
