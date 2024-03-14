package com.booking.site.dao.projection;

import com.booking.site.entity.Booking;
import com.booking.site.entity.Event;
import com.booking.site.entity.User;
import com.booking.site.serializer.UserBookingsSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "createdEventsProjection",types = {User.class})
public interface UserCreatedEventsProjection {



        List<Event> getCreatedEvents();

}
