package ro.ase.contranager.Contranager.controllers;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ase.contranager.Contranager.pojos.UserPojo;
import ro.ase.contranager.Contranager.services.LoginService;

@RestController
@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*")
public class LoginController {

  @Autowired
  private LoginService loginService;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<String> getLogin(@RequestBody UserPojo userPojo) {
      return loginService.doLogin(userPojo);
  }
}
