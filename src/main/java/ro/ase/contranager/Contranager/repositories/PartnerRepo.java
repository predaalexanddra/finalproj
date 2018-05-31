package ro.ase.contranager.Contranager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ase.contranager.Contranager.entities.Partner;

public interface PartnerRepo extends JpaRepository<Partner, Long> {
}
