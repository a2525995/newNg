package com.qa.api_test.propertiesLoader;


import java.io.*;
import java.util.Properties;

public abstract class PropertiesBase {
    public static final Properties properties = new Properties();


    public String getKey(String key) {
        return properties.getProperty(key);
    }

    public static void loadFile(String filePath) throws IOException {

        InputStream inputStream = new FileInputStream(new File(filePath));
        properties.load(inputStream);
    }

}
