package com.booking.site.dao.customdao;

import com.booking.site.entity.Wishlist;

import java.util.List;

public interface WishlistRepositoryCustom {
   List<Wishlist> findAll();
   Wishlist save(Wishlist w);
}
