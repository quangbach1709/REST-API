package com.quangbach1709.restapi.service;

import com.quangbach1709.restapi.dto.CompanyDTO;
import com.quangbach1709.restapi.dto.CompanyMapper;
import com.quangbach1709.restapi.entity.Company;
import com.quangbach1709.restapi.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(CompanyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Page<CompanyDTO> getAllCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable)
                .map(CompanyMapper::toDTO);
    }

    public CompanyDTO getCompanyById(Long id) {
        return companyRepository.findById(id)
                .map(CompanyMapper::toDTO)
                .orElse(null);
    }

    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        Company company = CompanyMapper.toEntity(companyDTO);
        company = companyRepository.save(company);
        return CompanyMapper.toDTO(company);
    }

    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {
        return companyRepository.findById(id)
                .map(existingCompany -> {
                    existingCompany.setName(companyDTO.getName());
                    existingCompany.setCode(companyDTO.getCode());
                    existingCompany.setAddress(companyDTO.getAddress());
                    Company updatedCompany = companyRepository.save(existingCompany);
                    return CompanyMapper.toDTO(updatedCompany);
                })
                .orElse(null);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }


}
