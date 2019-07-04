package com.qa.api_test.yamlParser;

import com.alibaba.fastjson.JSONObject;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataProvider extends DataProvider {
    private static TestDataProvider instance;

    private Map map;

    public static TestDataProvider getInstance() {
        if (instance == null) {
            synchronized (TestDataProvider.class) {
                if (instance == null) {
                    instance = new TestDataProvider();
                }
            }
        }
        return instance;
    }

    private TestDataProvider() {

    }

    public void setMap(String filePath) throws FileNotFoundException {
        map = loadYamlReturnMap(System.getProperty("user.dir") + "/src/test/TestSuiteData/" + filePath);
    }


    public Map getMap() {
        return map;
    }

    public List getApiInfoList() {
        List list = (List) map.get("api_infos");
        return list;
    }

    public HashMap readApiInfo(String key) {
        List list = getApiInfoList();
        for (Object object : list) {
            HashMap hashMap = (HashMap) object;
            if (hashMap.get("name").equals(key)) {
                return hashMap;
            }
        }
        return null;
    }

    public String readApiUrl(String key) {
        return readApiKey(key, "url");
    }

    public String readApiUrlByJson(String key) {
        return readApiKeyByJson(key, "url");
    }

    public String readApiData(String key) {
        return readApiKey(key, "data");
    }

    public String readApiDataByJson(String key) {
        return readApiKeyByJson(key, "data");
    }

    public String readApiKeyByJson(String key, String keywords) {
        HashMap<String, Object> hashMap = readApiInfo(key);
        if (null != hashMap) {
            return JSONObject.toJSONString(hashMap.get(keywords));
        }
        return null;
    }

    public String readApiKey(String key, String keywords) {
        HashMap<String, Object> hashMap = readApiInfo(key);
        if (null != hashMap) {
            return (hashMap.get(keywords).toString());
        }
        return null;
    }
}
