package ro.ase.contranager.Contranager.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.ase.contranager.Contranager.entities.Contract;

public interface ContractsRepo extends JpaRepository<Contract, Long> {

  @Query("select c from Contract c order by c.value desc ")
   List<Contract> findContractsOrdered();
}
