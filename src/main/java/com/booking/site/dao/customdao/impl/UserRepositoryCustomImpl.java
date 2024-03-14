package com.booking.site.dao.customdao.impl;

import com.booking.site.dao.AuthoritiesRepository;
import com.booking.site.dao.customdao.UserRepositoryCustom;
import com.booking.site.entity.Authrorities;
import com.booking.site.entity.User;
import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    @Autowired
    private AuthoritiesRepository ar;
  @Autowired
   private EntityManager em;
  @Autowired
  private PasswordEncoder passwordEncoder;
    @Override
   public User save(User u){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null)
        {
            em.persist(u);
            return u;
        }
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        em.persist(u);
        Authrorities a=new Authrorities();
        a.setUser(u);
        a.setRole("ROLE_USER");
        ar.save(a);
        return u;
    }

}
