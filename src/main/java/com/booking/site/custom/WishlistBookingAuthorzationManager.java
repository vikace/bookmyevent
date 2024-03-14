package com.booking.site.custom;

import com.booking.site.entity.Booking;
import com.booking.site.entity.User;
import com.booking.site.entity.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class WishlistBookingAuthorzationManager implements AuthorizationManager<RequestAuthorizationContext> {
    @Autowired
    wishlistBookingEventOwnerCreatorAuthorizationManager authorizationManager;
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        if(isAdmin(authentication.get()))
        {
            return new AuthorizationDecision(true);
        }
        User user=((UserInfo)authentication.get().getPrincipal()).getUser();
        int id=Integer.parseInt(object.getRequest().getRequestURI().split("/")[2]);
        Class<?> listClass=object.getRequest().getRequestURI().split("/")[1].equals("bookings")? Booking.class: Wishlist.class;

       return new AuthorizationDecision(authorizationManager.isDataOwner(user,id,listClass)) ;
    }
    private boolean isAdmin(Authentication authentication)
    {
        List<GrantedAuthority> grantedAuthorities=authentication.getAuthorities().stream().filter((e)->e.getAuthority().equals("ROLE_ADMIN")).collect(Collectors.toList());
        return !grantedAuthorities.isEmpty();
    }
}
