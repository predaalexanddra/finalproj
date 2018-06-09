package ro.ase.contranager.Contranager.services;

import java.sql.Timestamp;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ro.ase.contranager.Contranager.entities.Employee;
import ro.ase.contranager.Contranager.entities.Log;
import ro.ase.contranager.Contranager.pojos.UserPojo;
import ro.ase.contranager.Contranager.repositories.EmployeeRepo;
import ro.ase.contranager.Contranager.repositories.LogRepo;

@Service
public class LoginService {

  @Autowired
  private EmployeeRepo employeeRepo;

  @Autowired
  private LogRepo logRepo;

  public ResponseEntity<String> doLogin(UserPojo userPojo) {

    Employee user = employeeRepo.findEmployeeByUsername(userPojo.getUser());
    boolean ok=false;
    if(user!=null)
      ok= BCrypt.checkpw(userPojo.getPassword(),user.getPassword());

    if (ok) {
      Log log =new Log();
      log.setDate(new Timestamp(System.currentTimeMillis()));
      log.setEmployee(user);
      logRepo.save(log);
      if (user.isAdmin())
        return new ResponseEntity<>("{\"role\":\"admin\"}", HttpStatus.OK);
      else
        return new ResponseEntity<>("{\"role\":\"user\"}", HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }
}
