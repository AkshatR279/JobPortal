package com.akshatr.jobportal.repository;

import com.akshatr.jobportal.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
