package com.quangbach1709.restapi.service;

import com.quangbach1709.restapi.dto.CountryDTO;
import com.quangbach1709.restapi.dto.CountryMapper;
import com.quangbach1709.restapi.entity.Country;
import com.quangbach1709.restapi.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService{
    @Autowired
    private CountryRepository countryRepository;

    public List<CountryDTO> getAllCountries(){
        return countryRepository.findAll()
                .stream().map(CountryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CountryDTO getCountryById(Long id){
        return countryRepository.findById(id)
                .map(CountryMapper::toDTO)
                .orElse(null);
    }

    public CountryDTO createCountry(CountryDTO countryDTO){
        Country country = CountryMapper.toEntity(countryDTO);
        country = countryRepository.save(country);
        return CountryMapper.toDTO(country);
    }

    public CountryDTO updateCountry(Long id, CountryDTO countryDTO) {
        return countryRepository.findById(id)
                .map(country -> {
                    country.setName(countryDTO.getName());
                    country.setCode(countryDTO.getCode());
                    country.setDescription(countryDTO.getDescription());
                    country = countryRepository.save(country);
                    return CountryMapper.toDTO(country);
                })
                .orElse(null);
    }

    public void deleteCountry(Long id){
        countryRepository.findById(id)
                .ifPresent(country -> countryRepository.delete(country));

    }

}
