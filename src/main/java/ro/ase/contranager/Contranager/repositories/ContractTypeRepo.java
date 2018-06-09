package ro.ase.contranager.Contranager.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.ase.contranager.Contranager.entities.ContractType;


public interface ContractTypeRepo extends JpaRepository<ContractType, Long> {

  @Query("select c.name from ContractType c" )
  List<String> findContractsTypenames();

  ContractType findByName(String name);
}
