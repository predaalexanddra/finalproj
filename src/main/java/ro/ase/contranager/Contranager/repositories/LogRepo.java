package ro.ase.contranager.Contranager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ase.contranager.Contranager.entities.Log;

public interface LogRepo extends JpaRepository<Log,Long>{

}
