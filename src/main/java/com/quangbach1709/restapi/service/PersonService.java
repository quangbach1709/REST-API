package com.quangbach1709.restapi.service;

import com.quangbach1709.restapi.dto.PersonDTO;
import com.quangbach1709.restapi.dto.PersonMapper;
import com.quangbach1709.restapi.entity.Person;
import com.quangbach1709.restapi.repository.CompanyRepository;
import com.quangbach1709.restapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public List<PersonDTO> getAllPersons() {
        return personRepository.findAll().stream()
                .map(PersonMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Page<PersonDTO> getAllPersons(Pageable pageable) {
        return personRepository.findAll(pageable)
                .map(PersonMapper::toDTO);
    }

    public Page<PersonDTO> getPersonsByCompany(Long companyId, Pageable pageable) {
        return personRepository.findByCompanyId(companyId, pageable)
                .map(PersonMapper::toDTO);
    }

    public PersonDTO getPersonById(Long id) {
        return personRepository.findByIdWithCompany(id)
                .map(PersonMapper::toDTO)
                .orElse(null);
    }

    public PersonDTO createPerson(PersonDTO personDTO) {
        Person person = PersonMapper.toEntity(personDTO);
        if (personDTO.getCompany() != null) {
            person.setCompany(companyRepository.findById(personDTO.getCompany().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Company not found: " + personDTO.getCompany().getId())));

        }

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

                    if (personDTO.getCompany() != null) {
                        existingPerson.setCompany(companyRepository.findById(personDTO.getCompany().getId())
                                .orElseThrow(() -> new IllegalArgumentException("Company not found: " + personDTO.getCompany().getId())));
                    } else {
                        existingPerson.setCompany(null);
                    }
                    Person updatedPerson = personRepository.save(existingPerson);
                    return PersonMapper.toDTO(updatedPerson);
                })
                .orElse(null);
    }

    public PersonDTO updateAvatar(Long id, MultipartFile file) {
        return personRepository.findById(id)
                .map(person -> {
                    // Delete old avatar if exists
                    if (person.getAvatar() != null) {
                        fileStorageService.delete(person.getAvatar());
                    }

                    // Save new avatar
                    String filename = fileStorageService.save(file);
                    person.setAvatar(filename);

                    Person updatedPerson = personRepository.save(person);
                    return PersonMapper.toDTO(updatedPerson);
                })
                .orElseThrow(() -> new IllegalArgumentException("Person not found"));
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
