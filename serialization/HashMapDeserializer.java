package serialization;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import pojo.Product;

// ----------------- PURPOSE: Allow for HashMaps<Product, Integer> to be deserialized -----------------

public class HashMapDeserializer extends JsonDeserializer<HashMap<Product, Integer>>{
    
    @Override
    public HashMap<Product, Integer> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        HashMap<Product, Integer> hashMap = new HashMap<>();

        // as long as the parser has a next token and it isn't the end of the object
        while (jsonParser.nextToken() != null && jsonParser.currentToken() != JsonToken.END_OBJECT) {
            // skip a token (which says 'product1') and serialize it to a Product
            jsonParser.nextToken();
            Product value = jsonParser.readValueAs(Product.class);
            
            // skip two tokens (not exactly sure why, first one was 'quantity1', but at least it works :) )
            jsonParser.nextToken();
            jsonParser.nextToken();
            int quantity = jsonParser.readValueAs(Integer.class);
            hashMap.put(value, quantity);
        }
        return hashMap;
    }
}
