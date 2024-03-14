package com.booking.site.custom;

import com.booking.site.dao.BookingRepository;
import com.booking.site.dao.WishlistRepository;
import com.booking.site.entity.Booking;
import com.booking.site.entity.Event;
import com.booking.site.entity.User;
import com.booking.site.entity.Wishlist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
@Component
public class wishlistBookingEventOwnerCreatorAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
   private WishlistRepository wishlistRepository;
    @Autowired
    private BookingRepository bookingRepository;

    private class ListUtility {

        List<?> list;
        ListUtility(int id, User user,Class<?> listClass) {
            Session session= sessionFactory.openSession();
            Transaction tsn=session.beginTransaction();
            User u=session.merge(user);
            if(listClass.getSimpleName().equals(Wishlist.class.getSimpleName()))
            {
                list=u.getWishlists().stream().filter(e->e.getId()==id).toList();
            }
             else if(listClass.getSimpleName().equals(Booking.class.getSimpleName()))
            {
                list=u.getBookings().stream().filter(e->e.getId()==id).toList();
            }
             else
            {
                list=new ArrayList<>();
            }
            tsn.commit();
            session.close();

        }

        List<?> getList()
        {

            return this.list;
        }
    }
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {

        if(isAdmin(authentication.get()))
        {
            return new AuthorizationDecision(true);
        }

        User user = ((UserInfo) authentication.get().getPrincipal()).getUser();

        try
        {
            int id=Integer.parseInt(object.getRequest().getRequestURI().split("/")[2]);
            System.out.println(object.getRequest().getRequestURI());
            Class<?> listClass=object.getRequest().getRequestURI().split("/")[1].equals("bookings")?Booking.class:Wishlist.class;
            return new AuthorizationDecision(isEventCreator(user,id,listClass)||isDataOwner(user,id,listClass));
        }
        catch(Exception e)
        {
            System.out.println("Exception in "+this.getClass().getName()+" "+e);
            return new AuthorizationDecision(false);
        }

    }
    public boolean isDataOwner(User user,int id,Class<?> listClass)
    {
        return !(new ListUtility(id,user,listClass).getList().isEmpty());
    }
    private boolean isEventCreator(User user,int wishlistId,Class<?> entityClass)
    {
        Event event;
        if(entityClass.getSimpleName().equals(Wishlist.class.getSimpleName())) {
            Optional<Wishlist> optional= wishlistRepository.findById(wishlistId);
            if(optional.isEmpty())
            {
                return false;
            }
            event=optional.get().getEvent();
        }
        else
        {
            Optional<Booking> optional= bookingRepository.findById(wishlistId);
            if(optional.isEmpty())
            {
                return false;
            }
            event=optional.get().getEvent();
        }



        Session session= sessionFactory.openSession();
        Transaction tsn= session.beginTransaction();
        User u=session.merge(user);
       List<Event> events= u.getCreatedEvents().stream().filter(e->e.getId()== event.getId()).toList();
        tsn.commit();
        session.close();
        return !events.isEmpty();
    }
    private boolean isAdmin(Authentication authentication)
    {
        List<GrantedAuthority> grantedAuthorities=authentication.getAuthorities().stream().filter((e)->e.getAuthority().equals("ROLE_ADMIN")).collect(Collectors.toList());
        return !grantedAuthorities.isEmpty();
    }

}
