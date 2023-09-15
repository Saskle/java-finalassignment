package json;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import pojo.Product;

public class HashMapDeserializer extends JsonDeserializer<HashMap<Product, Integer>>{
    
    @Override
    public HashMap<Product, Integer> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        HashMap<Product, Integer> hashMap = new HashMap<>();

        while (jsonParser.nextToken() != null && jsonParser.currentToken() != JsonToken.END_OBJECT) {
            //String fieldName = jsonParser.getCurrentName(); // product1, product2, etc
            jsonParser.nextToken();
            Product value = jsonParser.readValueAs(Product.class);
            
            // skip two tokens (not exactly sure why, first one was quantity1)
            jsonParser.nextToken();
            //String fieldName = jsonParser.getCurrentName(); // quantity1, quantity2, etc
            jsonParser.nextToken();
            int quantity = jsonParser.readValueAs(Integer.class);
            hashMap.put(value, quantity);
        }
        return hashMap;
    }
}
