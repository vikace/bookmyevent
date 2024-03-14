package com.booking.site.serializer;

import com.booking.site.entity.Booking;
import com.booking.site.entity.Event;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class UserBookingsSerializer extends JsonSerializer<List<Booking>> {

    @Override
    public void serialize(List<Booking> bookings, JsonGenerator jg, SerializerProvider serializerProvider) throws IOException {
        JsonSerializer<Object> eventJsonSerializer=serializerProvider.findValueSerializer(Event.class) ;

        jg.writeStartArray();
        for (Booking b:
             bookings) {
            jg.writeStartObject();
            jg.writeFieldName("event");
            eventJsonSerializer.serialize(b.getEvent(),jg,serializerProvider);
            jg.writeStringField("time",b.getTime());
            jg.writeEndObject();
        }
        jg.writeEndArray();

    }

}
