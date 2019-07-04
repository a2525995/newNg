package com.qa.api_test.common;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.util.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientHelper {

    static final String jsonType = "application/json";

    static final String defaultCharset = "utf-8";

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    private static Map<String, String> map;


    public static List<String> GetRequestReturnByList(String url) throws Exception{
        String res = GetRequest(url);
        return JSONObject.parseArray(res, String.class);
    }

    public static Map<String, Object> GetRequestReturnByMap(String url) throws Exception {
        String res = GetRequest(url);
        return JSONObject.parseObject(res);
    }

    public static String GetRequest(String url) throws Exception {
        HttpUriRequest httpGet = new HttpGet(url);
        if (null != map) {
            setHeader(httpGet);
        }

        CloseableHttpResponse response = httpClient.execute(httpGet);

        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, defaultCharset);
    }

    public static List<String> PostRequestReturnByList(String url, String jsonString) throws Exception{
        String res = PostRequest(url, jsonString);
        return JSONObject.parseArray(res, String.class);
    }

    public static Map<String, Object> PostRequestReturnByMap(String url, String jsonString) throws Exception {

        String res = PostRequest(url, jsonString);
        return JSONObject.parseObject(res);

    }

    public static String PostRequest(String url, String jsonString) throws Exception {

        CloseableHttpResponse response = PostResponse(url, jsonString, false);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, defaultCharset);
    }

    public static Map<String, String> PostRequestGetHeader(String url, String headerKey, String jsonString) throws Exception {
        CloseableHttpResponse response = PostResponse(url, jsonString, false);
        Header header = response.getFirstHeader(headerKey);
        Map<String, String> map = new HashMap<String, String>() {
        };
        map.put(headerKey, header.getValue());

        return map;
    }

    public static String PostRequestByForm(String url, String jsonString) throws Exception{
        CloseableHttpResponse response = PostResponse(url, jsonString, true);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, defaultCharset);
    }

    public static byte[] PostRequestByFormReceivebytes(String url, String jsonString) throws Exception{
        CloseableHttpResponse response = PostResponse(url, jsonString, true);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toByteArray(entity);
    }

    private static CloseableHttpResponse PostResponse(String url, String jsonString, boolean isForm) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        if (map != null) {
            setHeader(httpPost);
        }
        StringEntity se;
        if (isForm){
            Map<String, Object> map = (Map) JSONArray.parse(jsonString);
            List<BasicNameValuePair> pair = new ArrayList<BasicNameValuePair>();
            for (Map.Entry<String, Object> entry: map.entrySet()){
                pair.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            se = new UrlEncodedFormEntity(pair);
        }
        else {
            se = new StringEntity(jsonString, Charset.forName(defaultCharset));
            se.setContentType(jsonType);

        }
        se.setContentEncoding(defaultCharset);
        httpPost.setEntity(se);
        return httpClient.execute(httpPost);
    }

    public static void setHeader(HttpUriRequest httpUriRequest) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            httpUriRequest.addHeader(entry.getKey(), entry.getValue());
        }
    }


    public static void setMap(Map<String, String> m) {
        map = m;

    }

    public static void cleanMap() {
        map = null;
    }

    public static Map<String, String> getMap() {
        return map;
    }


}
