package com.qa.api_test.yamlParser;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DataProvider {
    private static final Yaml yaml = new Yaml();

    public static Map<String, Object> loadYamlReturnMap(String filePath) throws FileNotFoundException {
        Map<String, Object> map;
        map = (Map<String, Object>) yaml.load(new FileInputStream(new File(filePath)));
        return map;
    }


}
