package ro.ase.contranager.Contranager.services;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ro.ase.contranager.Contranager.entities.Employee;
import ro.ase.contranager.Contranager.pojos.UserPOJO;
import ro.ase.contranager.Contranager.repositories.EmployeeRepo;

@Service
public class LoginService {

  @Autowired
  private EmployeeRepo employeeRepo;

  public ResponseEntity<String> doLogin(UserPOJO userPOJO) {

    Employee user = employeeRepo.findEmployeeByUsername(userPOJO.getUser());
    boolean ok= BCrypt.checkpw(userPOJO.getPassword(),user.getPassword());

    if (ok)
      if(user.isAdmin())
        return new ResponseEntity<>("admin", HttpStatus.OK);
      else
        return new ResponseEntity<>("user",HttpStatus.OK);

    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }
}
