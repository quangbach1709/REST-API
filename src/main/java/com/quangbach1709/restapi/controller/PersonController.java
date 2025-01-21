package com.quangbach1709.restapi.controller;

import com.quangbach1709.restapi.dto.PersonDTO;
import com.quangbach1709.restapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    @Autowired
    private PersonService personService;


    //    @GetMapping
//    public List<PersonDTO> getAllPersons() {
//        return personService.getAllPersons();
//    }
    @GetMapping
    public Page<PersonDTO> getAllPersons(Pageable pageable) {
        return personService.getAllPersons(pageable);
    }

    @GetMapping("/company/{companyId}")
    public Page<PersonDTO> getPersonsByCompany(@PathVariable Long companyId, Pageable pageable) {
        return personService.getPersonsByCompany(companyId, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        PersonDTO personDTO = personService.getPersonById(id);
        if (personDTO != null) {
            return ResponseEntity.ok(personDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public PersonDTO createPerson(@RequestBody PersonDTO personDTO) {
        return personService.createPerson(personDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        PersonDTO updatedPersonDTO = personService.updatePerson(id, personDTO);
        if (updatedPersonDTO != null) {
            return ResponseEntity.ok(updatedPersonDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
