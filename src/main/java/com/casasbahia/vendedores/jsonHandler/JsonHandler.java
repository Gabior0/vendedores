package com.casasbahia.vendedores.jsonHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readJsonFromFile(String filePath, Class<T> clazz) throws IOException {
        return objectMapper.readValue(new File(filePath), clazz);
    }
}
