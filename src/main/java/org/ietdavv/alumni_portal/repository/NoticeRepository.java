package org.ietdavv.alumni_portal.repository;


import org.ietdavv.alumni_portal.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
