package com.booking.site.serializer;

import com.booking.site.custom.SerializationViewContextHolder;
import com.booking.site.entity.User;
import com.booking.site.jsonViews.Views;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class UserSerializer extends JsonSerializer<User> {
@Autowired
    private ObjectMapper objectMapper;
    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            Class<?> view = SerializationViewContextHolder.getView();
           view= view==null? Views.userView.class:view;
           ObjectMapper localObjectMapper=new ObjectMapper();
           jsonGenerator.writeFieldName("user");
           localObjectMapper.writerWithView(view).writeValue(jsonGenerator,user);
    }
}
