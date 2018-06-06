package ro.ase.contranager.Contranager.controllers;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ase.contranager.Contranager.entities.Employee;
import ro.ase.contranager.Contranager.pojos.UserPOJO;
import ro.ase.contranager.Contranager.repositories.EmployeeRepo;
import ro.ase.contranager.Contranager.services.LoginService;

@RestController
public class LoginController {

  @Autowired
  private LoginService loginService;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<String> getLogin(@RequestBody UserPOJO userPOJO) {
      return loginService.doLogin(userPOJO);
  }
}
