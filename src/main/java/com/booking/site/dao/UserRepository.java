package com.booking.site.dao;

import com.booking.site.dao.customdao.UserRepositoryCustom;
import com.booking.site.dao.projection.*;
import com.booking.site.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Integer>, UserRepositoryCustom {
    UserBasicProjection findProjectionById(int id);
    UserAdvanceProjection findUserDetailsById(int id);
    UserWishlistProjection findWishlistsById(int id);
    UserBookingProjection findBookingsById(int id);

    UserCreatedEventsProjection findCreatedEventsById(int id);

}
