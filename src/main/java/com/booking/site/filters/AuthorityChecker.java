package com.booking.site.filters;

import com.booking.site.custom.UserInfo;
import com.booking.site.dao.WishlistRepository;
import com.booking.site.entity.Event;
import com.booking.site.entity.Wishlist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import com.booking.site.dao.EventRepository;

@Component
public class AuthorityChecker extends OncePerRequestFilter {

    @Autowired
   private EventRepository eventRepository;
    @Autowired
    WishlistRepository wishlistRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         if(request.getRequestURI().startsWith("/events/")&&request.getMethod().equals(HttpMethod.GET.name()))
        {
            int eventId=getEventId(request.getRequestURI());
            Optional<Event> e= eventRepository.findById(eventId);
            if(e.isPresent()&&!e.get().isCancelled())
            {
                Event event=e.get();
                int temp=event.getViews()+1;
                event.setViews(temp);
                eventRepository.saveAndFlush(event);
            }
            doFilter(request,response,filterChain);
        }

        else
        {
            doFilter(request,response,filterChain);
        }
    }
    private int getEventId(String uri)
    {
        return Integer.parseInt(uri.split("/")[2]);
    }
}
