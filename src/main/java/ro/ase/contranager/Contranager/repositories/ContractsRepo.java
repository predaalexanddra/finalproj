package ro.ase.contranager.Contranager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ase.contranager.Contranager.entities.Contract;

public interface ContractsRepo extends JpaRepository<Contract, Long> {
}
