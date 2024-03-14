package com.booking.site;
import com.booking.site.custom.JwtService;
import com.booking.site.dao.EventRepository;
import com.booking.site.dao.UserRepository;
import com.booking.site.custom.UserInfo;
import com.booking.site.dao.projection.UserAdvanceProjection;
import com.booking.site.dao.projection.UserBasicProjection;
import com.booking.site.dao.projection.UserBookingProjection;
import com.booking.site.dao.projection.UserWishlistProjection;
import com.booking.site.entity.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FeatureController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/user/login")
    public void processLogin(@RequestBody Credentials credentials, HttpServletResponse response)
    {

        Authentication authentication=new UsernamePasswordAuthenticationToken(credentials.getEmail(),credentials.getPassword());
       authentication= authenticationManager.authenticate(authentication);

          String token=jwtService.generateToken(((UserDetails)authentication.getPrincipal()).getUsername(),new HashMap<String, Object>());
         Cookie cookie=new Cookie("bookmyeventToken",token);
         cookie.setPath("/");
         cookie.setMaxAge(-1);
           response.addCookie(cookie);

    }
    @GetMapping ("user/logout")
    public void processLogin(HttpServletResponse response, @CookieValue(name = "bookmyeventToken") String token)throws IOException
    {
        if(token!=null) {
            Cookie cookie = new Cookie("bookmyeventToken", null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        else
        {
         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
         response.getWriter().write("Login first.");
        }
    }
    @GetMapping("/user/profileinfo")
    @Transactional
    public UserAdvanceProjection profileInfo(Principal principal)
    {
        return userRepository.findUserDetailsById(((UserInfo)((UsernamePasswordAuthenticationToken)principal).getPrincipal()).getUser().getId());
    }
    @GetMapping("/user/basicinfo")
    @Transactional
    public UserBasicProjection getId(Principal principal)
    {
        return userRepository.findProjectionById(((UserInfo)((UsernamePasswordAuthenticationToken)principal).getPrincipal()).getUser().getId());
    }
    @GetMapping("/trending/events")
    @Transactional
    public List<Event> trending()
    {
        return eventRepository.tending();
    }

}
