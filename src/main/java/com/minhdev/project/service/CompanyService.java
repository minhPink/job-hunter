package com.minhdev.project.service;

import com.minhdev.project.domain.Company;
import com.minhdev.project.domain.dto.Meta;
import com.minhdev.project.domain.dto.ResultPaginationDTO;
import com.minhdev.project.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public ResultPaginationDTO getAllCompanies(Specification<Company> spec,Pageable pageable) {
        Page<Company> page = companyRepository.findAll(spec, pageable);
        ResultPaginationDTO paginationDTO = new ResultPaginationDTO();
        Meta meta = new Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(page.getTotalPages());
        meta.setTotal(page.getTotalElements());

        paginationDTO.setMeta(meta);
        paginationDTO.setResult(page.getContent());

        return paginationDTO;
    }

    public Company handleUpdateCompany(Company company) {
        Optional<Company> companyOptional = this.companyRepository.findById(company.getId());
        if (companyOptional.isPresent()) {
            Company updatedCompany = companyOptional.get();
            updatedCompany.setName(company.getName());
            updatedCompany.setAddress(company.getAddress());
            updatedCompany.setDescription(company.getDescription());
            updatedCompany.setLogo(company.getLogo());
            return this.companyRepository.save(updatedCompany);
        }
        return null;
    }

    public void handleDeleteCompany(Long id) {
        this.companyRepository.deleteById(id);
    }
}
