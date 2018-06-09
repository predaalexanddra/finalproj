package ro.ase.contranager.Contranager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ase.contranager.Contranager.entities.Contract;
import ro.ase.contranager.Contranager.entities.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

  Employee findEmployeeByUsername(String username);
  Employee findByCnp(Long cnp);
}
