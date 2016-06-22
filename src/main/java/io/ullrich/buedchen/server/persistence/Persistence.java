package io.ullrich.buedchen.server.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Persistence {
   public final static String FILENAME = "data.json";

    public static void save(PersistenceObject persistenceObject) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(Persistence.FILENAME), persistenceObject);
    }

    public static PersistenceObject read() throws IOException, FileNotFoundException {
        File f = new File(Persistence.FILENAME);
        if(!f.exists()) {
            throw new FileNotFoundException(Persistence.FILENAME);
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(f, PersistenceObject.class);
    }
}
