package com.quangbach1709.restapi.controller;

import com.quangbach1709.restapi.dto.CompanyDTO;
import com.quangbach1709.restapi.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    //    @GetMapping
//    public List<CompanyDTO> getAllCompanies() {
//        return companyService.getAllCompanies();
//    }
    @GetMapping
    public Page<CompanyDTO> getAllCompanies(Pageable pageable) {
        return companyService.getAllCompanies(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {
        CompanyDTO companyDTO = companyService.getCompanyById(id);
        if (companyDTO != null) {
            return ResponseEntity.ok(companyDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public CompanyDTO createCompany(@RequestBody CompanyDTO companyDTO) {
        return companyService.createCompany(companyDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Long id, @RequestBody CompanyDTO companyDTO) {
        CompanyDTO updatedCompanyDTO = companyService.updateCompany(id, companyDTO);
        if (updatedCompanyDTO != null) {
            return ResponseEntity.ok(updatedCompanyDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
