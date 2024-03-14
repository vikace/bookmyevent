package com.booking.site.dao.customdao.impl;

import com.booking.site.custom.UserInfo;
import com.booking.site.dao.customdao.EventRepositoryCustom;
import com.booking.site.entity.Event;
import com.booking.site.entity.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

public class EventRepositoryCustomImpl implements EventRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public Event save(Event entity) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=((UserInfo)authentication.getPrincipal()).getUser();
        entity.setCreator(user);
        System.out.println("user "+user.getId());
        entityManager.persist(entity);
        return entity;

    }

}
