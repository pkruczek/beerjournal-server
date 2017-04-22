package com.beerjournal.infrastructure.mapping;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bson.types.ObjectId;

import java.io.IOException;

class ObjectIdSerializer extends StdSerializer<ObjectId> {

    ObjectIdSerializer(Class<ObjectId> t) {
        super(t);
    }

    @Override
    public void serialize(ObjectId value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.toString());
    }
}
