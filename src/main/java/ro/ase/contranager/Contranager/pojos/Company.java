package ro.ase.contranager.Contranager.pojos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import lombok.Data;

@Data
public class Company {

    private String name;
    private String email;
    private String address;
    private String phone;

    private static Company instance=null;
    private Company(String file) throws IOException {
      FileReader in = new FileReader(file);
      BufferedReader br = new BufferedReader(in);

     this.name=br.readLine();
     this.email=br.readLine();
     this.address=br.readLine();
     this.phone=br.readLine();

      in.close();
    }

    public synchronized static Company getInstance() {
      if (instance == null) {
        try {
          instance = new Company("D:\\JavaSchool\\contranager\\src\\main\\resources\\static\\company.txt");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return instance;
    }
}
