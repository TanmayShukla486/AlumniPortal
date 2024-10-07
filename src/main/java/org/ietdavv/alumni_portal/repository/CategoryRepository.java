package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategory(String category);
}
