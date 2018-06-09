package ro.ase.contranager.Contranager.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.ase.contranager.Contranager.entities.Department;

public interface DepartmentRepo extends JpaRepository<Department,Long> {

  Department findByName(String name);

  @Query("select d.name from Department d")
  List<String> findDepartmentsName();
}
