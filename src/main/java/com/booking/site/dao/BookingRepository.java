package com.booking.site.dao;

import com.booking.site.dao.customdao.BookingRepositoryCustom;
import com.booking.site.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BookingRepository extends JpaRepository<Booking,Integer>, BookingRepositoryCustom {
}
