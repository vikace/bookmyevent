package com.booking.site.dao.customdao.impl;

import com.booking.site.custom.UserInfo;
import com.booking.site.dao.customdao.BookingRepositoryCustom;
import com.booking.site.dao.customdao.EventRepositoryCustom;
import com.booking.site.entity.Booking;
import com.booking.site.entity.Event;
import com.booking.site.entity.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.booking.site.dao.EventRepository;

import java.util.Optional;

public class BookingRepositoryCustomImpl implements BookingRepositoryCustom {
    @Autowired
    EntityManager em;
    @Override
    public Booking save(Booking b) {

         Event e=em.find(Event.class,b.getEvent().getId());
         if(e!=null)
         {
             Authentication auth= SecurityContextHolder.getContext().getAuthentication();
             User u=((UserInfo)auth.getPrincipal()).getUser();

             b.setUser(u);
             em.persist(b);
             e.setOccupied(e.getOccupied()+1);
             em.persist(e);
             return b;
         }
         else
         {
             return null;
         }

    }
}
