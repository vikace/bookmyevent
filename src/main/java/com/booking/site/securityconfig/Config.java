package com.booking.site.securityconfig;

import com.booking.site.custom.EventCreatorAuthorizationManager;
import com.booking.site.custom.UserIdAuthorization;
import com.booking.site.custom.WishlistBookingAuthorzationManager;
import com.booking.site.custom.wishlistBookingEventOwnerCreatorAuthorizationManager;
import com.booking.site.entity.Event;
import com.booking.site.entity.User;
import com.booking.site.filters.AuthorityChecker;
import com.booking.site.filters.JsonViewSetterFilter;
import com.booking.site.filters.JwtAuthFilter;
import com.booking.site.serializer.EventSerializer;
import com.booking.site.serializer.UserSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Configuration
public class Config {
    @Autowired
    JwtAuthFilter jwtAuthFilter;
    @Autowired
    AuthorityChecker authorityChecker;
    @Autowired
    JsonViewSetterFilter jsonViewSetterFilter;
    @Autowired
    UserIdAuthorization userIdAuthorizationManager;
    @Autowired
    EventCreatorAuthorizationManager eventCreatorAuthorizationManager;
    @Autowired
    wishlistBookingEventOwnerCreatorAuthorizationManager wishlistAccessAuthorizationManager;
    @Autowired
    WishlistBookingAuthorzationManager wishlistBookingAuthorzationManager;
    @Bean
   public SecurityFilterChain securityConfiguration(HttpSecurity http)throws Exception
    {
        http.formLogin(form->{
            form.loginPage("/login");
        });
        http.authorizeHttpRequests(request->{
            request.requestMatchers(HttpMethod.POST,"/events","/wishlists","/bookings").hasAnyRole("USER","ADMIN")
                   .requestMatchers(HttpMethod.GET,"/events/{id}/wishlists","/events/{id}/bookings").access(eventCreatorAuthorizationManager)
                    .requestMatchers(HttpMethod.PUT,"/events/{id}").access(eventCreatorAuthorizationManager)
                    .requestMatchers(HttpMethod.PATCH,"/events/{id}").access(eventCreatorAuthorizationManager)
                    .requestMatchers(HttpMethod.DELETE,"events/{id}").access(eventCreatorAuthorizationManager)
                    .requestMatchers(HttpMethod.GET,"/users/{id}","/users/{id}/createdEvents","/users/{id}/bookings","/users/{id}/wishlists","/users/{id}/authrorities").access(userIdAuthorizationManager)
                    .requestMatchers(HttpMethod.PUT,"/users/{id}").access(userIdAuthorizationManager)
                    .requestMatchers(HttpMethod.PATCH,"/users/{id}").access(userIdAuthorizationManager)
                    .requestMatchers(HttpMethod.DELETE,"/users/{id}").access(userIdAuthorizationManager)
                    .requestMatchers(HttpMethod.GET,"wishlists/{id}/event","wishlists/{id}/user","bookings/{id}/event","bookings/{id}/user").access(wishlistAccessAuthorizationManager)
                    .requestMatchers(HttpMethod.DELETE,"/wishlists/{id}","bookings/{id}").access(wishlistBookingAuthorzationManager)
                    .requestMatchers(HttpMethod.GET,"/user/profileinfo","user/basicinfo","user/logout").hasRole("USER")
                    .requestMatchers(HttpMethod.GET,"/events","/events/{id}","/events/{id}/creator","/features").permitAll()
                    .requestMatchers(HttpMethod.POST,"/users").permitAll()
                    .requestMatchers("/user/login","/user/logout","/trending/events","/","/js/**","/css/**").permitAll()
                    .anyRequest().hasRole("ADMIN");

        });
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(authorityChecker,UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jsonViewSetterFilter,UsernamePasswordAuthenticationFilter.class);
        http.csrf(csrf->csrf.disable());
        http.sessionManagement(con->{con.sessionCreationPolicy(SessionCreationPolicy.STATELESS);});
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService)
    {
        DaoAuthenticationProvider ap=new DaoAuthenticationProvider();
        ap.setPasswordEncoder(passwordEncoder());
        ap.setUserDetailsService(userDetailsService);
        return ap;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration ac)throws Exception
    {
             return ac.getAuthenticationManager();
    }
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint()
    {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Authentication Error");
            }
        };
    }
    @Bean
    @Primary
    public ObjectMapper objectMapper()
    {
        SimpleDateFormat customDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.setDateFormat(customDateFormat);
        SimpleModule module=new SimpleModule();
        module.addSerializer(Event.class,new EventSerializer());
        module.addSerializer(User.class,new UserSerializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }

}
