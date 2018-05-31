package ro.ase.contranager.Contranager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ase.contranager.Contranager.entities.Contract;
import ro.ase.contranager.Contranager.repositories.ContractsRepo;

import java.util.List;

@RestController
public class ContractController {
    @Autowired
    ContractsRepo contractsRepo;

    @GetMapping(value="/contracts")
    public List<Contract> findaAll(){
        return contractsRepo.findAll();
    }
}
