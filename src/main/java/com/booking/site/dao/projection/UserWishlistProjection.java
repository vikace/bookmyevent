package com.booking.site.dao.projection;

import com.booking.site.entity.User;
import com.booking.site.entity.Wishlist;
import org.springframework.data.rest.core.config.Projection;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Projection(name = "userWishlistProjection",types = {User.class})
public interface UserWishlistProjection {
    List<Wishlist> getWishlists();
}
