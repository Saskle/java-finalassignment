package repository;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import pojo.Order;

// ----------------- PURPOSE: Reading & Writing to JSON -----------------

public class JSONhandler {
    
    // mapping object that allows serializing & deserializing POJO's (JavaTimeModule for LocalDateTime support)
    private ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
    private File file;

    public void saveJSON(Order order) {
        mapper.writeValue(file, order);
    }

    public Order readJSON() {
        Order order = mapper.readValue(file, Order.class);
    }
}
