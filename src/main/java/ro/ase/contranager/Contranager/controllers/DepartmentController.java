package ro.ase.contranager.Contranager.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ase.contranager.Contranager.repositories.DepartmentRepo;


@RestController
@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*")
public class DepartmentController {
  @Autowired
  private DepartmentRepo departmentRepo;

  @GetMapping(value="/departments")
  public List<String> findDepartments(){
    return departmentRepo.findDepartmentsName();
  }
}
