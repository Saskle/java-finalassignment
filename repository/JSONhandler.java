package repository;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// ----------------- PURPOSE: Abstract class for reading & writing to JSON -----------------

public abstract class JSONhandler<T> {
    
    // mapping object that allows serializing & deserializing POJO's (JavaTimeModule for LocalDateTime support)
    protected ObjectMapper mapper = JsonMapper.builder()
                                                .addModule(new JavaTimeModule())
                                                .build();
    protected File file;

    public abstract void saveJSON(T object);
    public abstract T readJSON();

}
