package org.devgateway.tcdi.commons.pojo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ResponseSerializer extends StdSerializer<Response> {


    protected ResponseSerializer(Class<Response> t) {
        super(t);
    }

    protected ResponseSerializer() {
        this(null);
    }


    @Override
    public void serialize(final Response response, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        if (response.getType() != null) {
            jsonGenerator.writeObjectField("name", response.getType());
        }
        if (response.getValue() != null) {
            jsonGenerator.writeObjectField("value", response.getValue());
        }
        if (response.getCount() != null) {
            jsonGenerator.writeObjectField("count", response.getCount());
        }
        if (response.getSum() != null) {
            jsonGenerator.writeObjectField("sum", response.getSum());
        }
        if (response.getChildren() != null) {
            jsonGenerator.writeObjectField("items", response.getChildren());
        }
        jsonGenerator.writeEndObject();
    }


}
