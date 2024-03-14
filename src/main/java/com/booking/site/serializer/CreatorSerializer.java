package com.booking.site.serializer;

import com.booking.site.entity.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Base64;

public class CreatorSerializer extends JsonSerializer<User> {
    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        System.out.println("hello");
        jsonGenerator.writeStartObject();


        jsonGenerator.writeStringField("name",user.getFname()+" "+user.getLname());
        if(user.getPfp()!=null)
        jsonGenerator.writeStringField("pfp", Base64.getEncoder().encodeToString(user.getPfp()));
        jsonGenerator.writeEndObject();
    }

}
