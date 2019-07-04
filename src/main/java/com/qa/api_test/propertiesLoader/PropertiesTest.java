package com.qa.api_test.propertiesLoader;

import java.io.IOException;

public class PropertiesTest extends PropertiesBase {

    public static final String defaultPath = String.format("%s/%s", System.getProperty("user.dir"), "src/main/resources/sophon.properties");

    public static final String httpHost = "test.env.http.host";

    public static final String httpPort = "test.env.http.port";

    public String getUsername() {
        return properties.getProperty("test.username");
    }

    public String getPassword() {
        return properties.getProperty("test.password");
    }

    private static PropertiesTest instance = new PropertiesTest();

    public String getUrl() {
        return String.format("%s:%s", properties.getProperty(httpHost), properties.getProperty(httpPort));
    }

    private PropertiesTest() {
    }

    public void loadConfig() throws IOException {
        loadFile(defaultPath);
    }

//    public void loadConfig(String ConfigPath) throws IOException{
//        PropertiesBase.loadFile(ConfigPath);
//    }


    public static PropertiesTest getInstance() {
        if (instance == null) {
            synchronized (PropertiesTest.class) {
                if (instance == null) {
                    instance = new PropertiesTest();
                }
            }
        }
        return instance;
    }


}
