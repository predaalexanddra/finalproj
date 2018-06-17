package ro.ase.contranager.Contranager.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.ase.contranager.Contranager.entities.Employee;
import ro.ase.contranager.Contranager.pojos.EmployeePojo;
import ro.ase.contranager.Contranager.services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping(value = "/adduser")
  public ResponseEntity<String> addNewUser(@RequestBody  EmployeePojo employeePojo){
    Employee result= userService.addEmployee(employeePojo);
    if(result!=null)
      return new ResponseEntity<>(HttpStatus.OK);
    else
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value="/deleteuser")
  public ResponseEntity<String> deleteUser(@RequestParam(name = "phone")String phone){
    Employee result=userService.deleteEmployee(phone);
    if(result!=null)
      return new ResponseEntity<>(HttpStatus.OK);
    else
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value="/users")
  public List<EmployeePojo> findAll(){
    return userService.displayAll();
  }
}
