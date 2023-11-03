package com.stockapi.service;

import com.stockapi.dto.CompanyDTO;
import com.stockapi.model.Company;
import com.stockapi.repository.CompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ModelMapper modelMapper;

    public CompanyDTO getCompanyInfo(String symbol){
        Company company = companyRepository.findCompanyByStock(symbol);
        return modelMapper.map(company, CompanyDTO.class);
    }

}
