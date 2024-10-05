package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
