package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// ----------------- PURPOSE: Reading & Writing to JSON -----------------

public class JSONhandler {
    
    // mapping object that allows serializing & deserializing POJO's (JavaTimeModule for LocalDateTime support)
    private ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    public void saveJSON() {

    }

    public void readJSON() {

    }
}
