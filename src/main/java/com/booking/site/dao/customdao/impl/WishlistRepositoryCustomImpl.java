package com.booking.site.dao.customdao.impl;

import com.booking.site.custom.UserInfo;
import com.booking.site.dao.customdao.WishlistRepositoryCustom;
import com.booking.site.entity.User;
import com.booking.site.entity.Wishlist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class WishlistRepositoryCustomImpl implements WishlistRepositoryCustom {
    @Autowired
    EntityManager entityManager;
    @Override
    public List<Wishlist> findAll() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        int uid=((UserInfo)authentication.getPrincipal()).getUser().getId();
       TypedQuery<Wishlist> query= entityManager.createQuery("SELECT w FROM wishlist w WHERE user_id=:uid",Wishlist.class);
       query.setParameter("uid",uid);
      return query.getResultList();

    }
    @Override
    public Wishlist save(Wishlist w)
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User u=((UserInfo)authentication.getPrincipal()).getUser();
        w.setUser(u);
        entityManager.persist(w);
        return w;
    }

}
