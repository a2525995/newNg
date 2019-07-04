package test_project;

import com.qa.api_test.propertiesLoader.PropertiesTest;
import com.qa.api_test.yamlParser.TestDataProvider;
import com.qa.api_test.common.ApiHttpClient;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class Department {
    private String url;
    private TestDataProvider testDataProvider;

    @BeforeClass
    public void beforeTest() throws Exception{
        PropertiesTest propertiesTest = PropertiesTest.getInstance();
        propertiesTest.loadConfig();
        url = propertiesTest.getUrl();
        testDataProvider = TestDataProvider.getInstance();
        testDataProvider.setMap("department.yml");
    }

    @Test
    public void DepartmentSubList() throws Exception{
        String requestUrl = url + testDataProvider.readApiUrl("sub_list");
        List res = ApiHttpClient.GetRequestReturnByList(requestUrl);
        assert res.size() != 0;
    }

    @Test
    public void DepartmentAllSubList() throws Exception{
        String requestUrl = url + testDataProvider.readApiUrl("all_sub_list");
        Map res = ApiHttpClient.GetRequestReturnByMap(requestUrl);
        System.out.println(res);
        assert res.toString().indexOf("总局") > 0;
    }



}
