package json;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import pojo.Product;

public class HashMapSerializer extends JsonSerializer<HashMap<Product, Integer>> {
    
    @Override
    public void serialize(HashMap<Product, Integer> hashMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        int productCount = 0;
        jsonGenerator.writeStartObject();

        for (Product key : hashMap.keySet()) {
            productCount++;
            int value = hashMap.get(key);

            jsonGenerator.writeObjectField("product" + productCount, key);
            jsonGenerator.writeNumberField("quantity" + productCount, value);
        }
        jsonGenerator.writeEndObject();
    }
}
