package com.booking.site.dao;

import com.booking.site.dao.customdao.EventRepositoryCustom;
import com.booking.site.entity.Event;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Integer>, EventRepositoryCustom {
    @Procedure("trending")

    List<Event> tending();
}
