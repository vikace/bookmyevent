package com.booking.site.dao;

import com.booking.site.dao.customdao.WishlistRepositoryCustom;
import com.booking.site.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Integer>, WishlistRepositoryCustom {
}
