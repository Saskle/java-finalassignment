package serialization;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import pojo.Product;

// ----------------- PURPOSE: Allow for HashMaps<Product, Integer> to be serialized -----------------

public class HashMapSerializer extends JsonSerializer<HashMap<Product, Integer>> {
    
    @Override
    public void serialize(HashMap<Product, Integer> hashMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // initialise a product count so we can increment the key name
        int productCount = 0;
        jsonGenerator.writeStartObject();

        // for each product in the HashMap, create a new key product + count for the product, and quantity + count for its quantity
        for (Product key : hashMap.keySet()) {
            productCount++;
            int value = hashMap.get(key);

            jsonGenerator.writeObjectField("product" + productCount, key);
            jsonGenerator.writeNumberField("quantity" + productCount, value);
        }
        jsonGenerator.writeEndObject();
    }
}
