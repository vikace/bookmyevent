package com.booking.site.dao.service;

import com.booking.site.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.booking.site.dao.UserRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public List<Event> getCreatedEvents(int id)
    {
        return userRepository.findById(id).get().getCreatedEvents();
    }
}
