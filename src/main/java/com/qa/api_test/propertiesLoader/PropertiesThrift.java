package com.qa.api_test.propertiesLoader;

import java.io.IOException;

public class PropertiesThrift extends PropertiesBase {
    public static final String thriftHost = "thrift.host";

    public static final String thriftPort = "thrift.port";

    public static final String defaultPath = String.format("%s/%s", System.getProperty("user.dir"), "src/main/resources/thrift_client.properties");

    private static PropertiesThrift instance = new PropertiesThrift();

    private PropertiesThrift() {
    }

    public static void loadConfig() throws IOException {
        loadFile(defaultPath);
    }

    public static PropertiesThrift getInstance() {
        if (instance == null) {
            synchronized (PropertiesThrift.class) {
                if (instance == null) {
                    instance = new PropertiesThrift();
                }
            }
        }
        return instance;
    }

    public static String getHost() {
        return properties.getProperty(thriftHost);
    }

    public static int GetPort() {
        return Integer.parseInt(properties.getProperty(thriftPort));
    }


}
