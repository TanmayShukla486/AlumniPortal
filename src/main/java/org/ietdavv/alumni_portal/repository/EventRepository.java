package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
