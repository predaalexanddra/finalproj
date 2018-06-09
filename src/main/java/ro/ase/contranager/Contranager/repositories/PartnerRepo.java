package ro.ase.contranager.Contranager.repositories;


import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.ase.contranager.Contranager.entities.Partner;
@Transactional
public interface PartnerRepo extends JpaRepository<Partner, Long> {

   Partner findByCui(Long cui);
   Partner findByName(String name);
   @Query("select p.name from Partner p" )
   List<String> findPartnersNames();



}

