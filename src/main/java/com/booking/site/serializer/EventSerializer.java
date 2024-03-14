package com.booking.site.serializer;

import com.booking.site.custom.SerializationViewContextHolder;
import com.booking.site.entity.Event;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class EventSerializer extends JsonSerializer<Event> {

    @Override
    public void serialize(Event event, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        if(SerializationViewContextHolder.getView()!=null) {
            System.out.println(SerializationViewContextHolder.getView().getName()+" "+event.getId());
            jsonGenerator.writeFieldName("event");
           ObjectWriter objectWriter= objectMapper.writerWithView(SerializationViewContextHolder.getView());
           objectWriter.writeValue(jsonGenerator,event);
        }
        else
        {

            objectMapper.writer().writeValue(jsonGenerator,event);
        }

    }
}
