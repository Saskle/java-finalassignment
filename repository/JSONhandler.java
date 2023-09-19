package repository;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// ----------------- PURPOSE: Reading & Writing to JSON -----------------

public abstract class JSONhandler {
    
    // mapping object that allows serializing & deserializing POJO's (JavaTimeModule for LocalDateTime support)
    protected ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
    protected File file;

    // public abstract void saveJSON(Object object);
    // public abstract Object readJSON();

}
