package ro.ase.contranager.Contranager.services;

import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ase.contranager.Contranager.entities.Department;
import ro.ase.contranager.Contranager.entities.Employee;
import ro.ase.contranager.Contranager.pojos.EmployeePojo;
import ro.ase.contranager.Contranager.pojos.EmployeePojoSimplified;
import ro.ase.contranager.Contranager.repositories.DepartmentRepo;
import ro.ase.contranager.Contranager.repositories.EmployeeRepo;

@Service
public class UserService {
  @Autowired
  private EmployeeRepo employeeRepo;
  @Autowired
  private DepartmentRepo departmentRepo;

  public Employee addEmployee(EmployeePojo employeePojo){
    Department department=departmentRepo.findByName(employeePojo.getDepartment());
    String password=employeePojo.getPassword();
    String salt = BCrypt.gensalt(12);
    String hashPass= BCrypt.hashpw(password, salt);
    Employee employee=new Employee(employeePojo.getCnp(),employeePojo.getName(),employeePojo.getPhone(),
        employeePojo.isAdmin(),employeePojo.getEmail(),hashPass,department);
    return employeeRepo.save(employee);
  }

  public Employee deleteEmployee(Long cnp){
    Employee employee= employeeRepo.findByCnp(cnp);
    if(employee!=null) {
      employeeRepo.delete(employee);
      return employee;
    }
    else
      return null;
  }

  public List<EmployeePojoSimplified> displayAll(){
    List<Employee> employees=employeeRepo.findAll();
    List<EmployeePojoSimplified> pojos=new ArrayList<>();
    for (Employee employee:employees
    ) {
      EmployeePojoSimplified pojo=new EmployeePojoSimplified();
      pojo.setName(employee.getName());
      pojo.setEmail(employee.getUsername());
      pojo.setPhone(employee.getPhone());
      pojo.setDepartment(employee.getDepartment().getName());
      pojo.setAdmin(employee.isAdmin());
      pojos.add(pojo);
    }
    return pojos;
  }
}
