package com.qa.api_test.report;

import com.qa.api_test.propertiesLoader.PropertiesTest;
import com.vimalselvam.testng.SystemInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MySystemInfo implements SystemInfo {
    @Override
    public Map<String, String> getSystemInfo() {
        PropertiesTest propertiesTest = PropertiesTest.getInstance();
        Map<String, String> systemInfo = new HashMap<>();
        try {
            propertiesTest.loadConfig();
            systemInfo.put("environment", propertiesTest.getUrl());
            systemInfo.put("测试人员", "紫薇");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return systemInfo;
    }
}
