package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
