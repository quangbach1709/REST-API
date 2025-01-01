package com.quangbach1709.restapi.service;

import com.quangbach1709.restapi.dto.PersonDTO;
import com.quangbach1709.restapi.dto.PersonMapper;
import com.quangbach1709.restapi.entity.Person;
import com.quangbach1709.restapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<PersonDTO> getAllPersons() {
        return personRepository.findAll().stream()
                .map(PersonMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO getPersonById(Long id) {
        return personRepository.findById(id)
                .map(PersonMapper::toDTO)
                .orElse(null);
    }

    public PersonDTO createPerson(PersonDTO personDTO) {
        Person person = PersonMapper.toEntity(personDTO);
        person = personRepository.save(person);
        return PersonMapper.toDTO(person);
    }

    public PersonDTO updatePerson(Long id, PersonDTO personDTO) {
        return personRepository.findById(id)
                .map(existingPerson -> {
                    existingPerson.setFullName(personDTO.getFullName());
                    existingPerson.setGender(personDTO.getGender());
                    existingPerson.setBirthDate(personDTO.getBirthdate());
                    existingPerson.setPhoneNumber(personDTO.getPhoneNumber());
                    existingPerson.setAddress(personDTO.getAddress());
                    Person updatedPerson = personRepository.save(existingPerson);
                    return PersonMapper.toDTO(updatedPerson);
                })
                .orElse(null);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
