package test_project;

import com.qa.api_test.common.ApiHttpClient;
import com.qa.api_test.propertiesLoader.PropertiesTest;
import com.qa.api_test.yamlParser.TestDataProvider;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class Suspect {
    private String url;
    private TestDataProvider testDataProvider;
    private String suspectId;

    @BeforeClass
    public void beforeTest() throws Exception{
        PropertiesTest propertiesTest = PropertiesTest.getInstance();
        propertiesTest.loadConfig();
        url = propertiesTest.getUrl();
        testDataProvider = TestDataProvider.getInstance();
        testDataProvider.setMap("suspect.yml");
    }

    // 查询嫌疑人
    @Test
    public void testListSuspect() throws Exception{
        String requestUrl = url + testDataProvider.readApiUrl("list_suspect") + testDataProvider.readApiKey("list_suspect", "pageIndex") + "/" + testDataProvider.readApiKey("list_suspect", "pageSize");
        String data = testDataProvider.readApiDataByJson("list_suspect");
        Map res = ApiHttpClient.PostRequestReturnByMap(requestUrl, data);
        Map m = (Map) res.get("data");
        List list = (List) m.get("modelList");
        m = (Map) list.get(0);
        suspectId = (String) m.get("id");
        assert !suspectId.equals("");
    }

//    @Test
//    public void testExportExcel() throws Exception{
//        String requestUrl = url + testDataProvider.readApiUrl("export_excel");
//        String data = testDataProvider.readApiDataByJson("export_excel");
//        Map res = ApiHttpClient.PostRequestReturnByMap(requestUrl, data);
//        System.out.println(res);
//
//    }

    // 添加到情报列表
    @Test(priority = 1)
    public void testAddIntelligence() throws Exception{
        String requestUrl = url + testDataProvider.readApiUrl("add_intelligence") + suspectId;
        String data = "";
        Map res = ApiHttpClient.PostRequestReturnByMap(requestUrl, data);
        String code = res.get("code").toString();
        assert code.equals("100000");

    }

    // 编辑保存备注
    @Test(priority = 1)
    public void testSaveNote() throws Exception{
        String requestUrl = url + testDataProvider.readApiUrl("save_note") + suspectId;
        String data = testDataProvider.readApiDataByJson("save_note");
        Map res = ApiHttpClient.PostRequestReturnByMap(requestUrl, data);
        assert res.get("code").equals("100000");
    }

    // 获取备注
    @Test(priority = 1)
    public void testGetNode() throws Exception{
        String requestUrl = url + testDataProvider.readApiUrl("get_note") + suspectId;
        Map res = ApiHttpClient.GetRequestReturnByMap(requestUrl);
        assert res.get("code").equals("100000");
    }

    @AfterClass
    public void afterTest() throws Exception{

    }

}
