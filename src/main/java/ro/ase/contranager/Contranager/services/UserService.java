package ro.ase.contranager.Contranager.services;

import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ase.contranager.Contranager.entities.Department;
import ro.ase.contranager.Contranager.entities.Employee;
import ro.ase.contranager.Contranager.pojos.Company;
import ro.ase.contranager.Contranager.pojos.EmployeePojo;
import ro.ase.contranager.Contranager.repositories.DepartmentRepo;
import ro.ase.contranager.Contranager.repositories.EmployeeRepo;

@Service
public class UserService {
  @Autowired
  private EmployeeRepo employeeRepo;
  @Autowired
  private DepartmentRepo departmentRepo;
  @Autowired
  private MailSender mailSender;

  public Employee addEmployee(EmployeePojo employeePojo){
    Department department=departmentRepo.findByName(employeePojo.getDepartment());
    String password=employeePojo.getPassword();
    String salt = BCrypt.gensalt(12);
    String hashPass= BCrypt.hashpw(password, salt);
    Employee employee=new Employee(employeePojo.getCnp(),employeePojo.getName(),employeePojo.getPhone(),
        employeePojo.getIsAdmin()=="Yes"?true:false,employeePojo.getEmail(),hashPass,department);
    String email=employee.getUsername();
    String body="Bine ati venit, "+employee.getName()+"!"+
        "\n"+"Contul dvs. a fost creat cu parola implicita \""+ employeePojo.getPassword()
        +"\". Va rugam, modificati-o la prima autentificare.\n"+
        "Multumim ca ati ales CONTRANAGER!";
    mailSender.sendMail(Company.getInstance().getEmail(),email,"Cont nou", body);
    return employeeRepo.save(employee);
  }

  public Employee deleteEmployee(String phone){
    Employee employee= employeeRepo.findByPhone(phone);
    if(employee!=null) {
      employeeRepo.delete(employee);
      return employee;
    }
    else
      return null;
  }

  public List<EmployeePojo> displayAll(){
    List<Employee> employees=employeeRepo.findAll();
    List<EmployeePojo> pojos=new ArrayList<>();
    for (Employee employee:employees
    ) {
      EmployeePojo pojo=new EmployeePojo();
      pojo.setName(employee.getName());
      pojo.setEmail(employee.getUsername());
      pojo.setPhone(employee.getPhone());
      pojo.setDepartment(employee.getDepartment().getName());
      pojo.setIsAdmin((employee.isAdmin()?"Yes":"No"));
      pojo.setCnp(employee.getCnp());
      pojo.setDepartment(employee.getDepartment().getName());
      pojos.add(pojo);
    }
    return pojos;
  }
}
