package ro.ase.contranager.Contranager.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.ase.contranager.Contranager.entities.Contract;

public interface ContractRepo extends JpaRepository<Contract, Long> {

  @Query("select c from Contract c where c.isCompleted=0 order by c.endDate asc ")
  List<Contract> findContractsOrdered();
  List<Contract> findContractsByIsCompletedIsFalse();
  Contract findByNoContract(Long no);

  @Query("select c from Contract c order by c.startDate asc ")
  List<Contract> findContractsOrderedByYear();


  @Query("select c from Contract c order by c.value desc ")
  List<Contract> findContractsValues();


}
