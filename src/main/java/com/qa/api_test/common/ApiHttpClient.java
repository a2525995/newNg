package com.qa.api_test.common;

import com.alibaba.fastjson.JSONObject;
import com.qa.api_test.propertiesLoader.PropertiesTest;

import java.util.HashMap;
import java.util.Map;

public class ApiHttpClient extends HttpClientHelper {
    private static PropertiesTest propertiesTest;

    private static ApiHttpClient instance = null;


    private ApiHttpClient() {
    }

    public static ApiHttpClient getInstance() {
        if (instance == null) {
            synchronized (ApiHttpClient.class) {
                if (instance == null) {
                    instance = new ApiHttpClient();
                }
            }
        }
        return instance;
    }

    public static void setToken() throws Exception {
        Map m = getMap();
        if (m != null && m.containsKey("SOPHON-Auth-Token")){
            return ;
        }
        propertiesTest = PropertiesTest.getInstance();
        propertiesTest.loadConfig();
        String tokenUrl = String.format("%s/user/login/pw/4", propertiesTest.getUrl());
        String username = propertiesTest.getUsername();
        String password = propertiesTest.getPassword();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);

        setMap(new HashMap<String, String>(PostRequestGetHeader(tokenUrl, "SOPHON-Auth-Token", jsonObject.toJSONString())));


        //String res = PostRequest(url, jsonObject);

    }

    public static String getToken() throws Exception{
        Map m = getMap();
        try {
            return m.get("SOPHON-Auth-Token").toString();
        }
        catch (Exception e){

        }
        return "";
    }




}
