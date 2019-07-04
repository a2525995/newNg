package test_project;

import com.qa.api_test.common.ApiHttpClient;
import org.testng.annotations.Test;

public class BeforeTestSuite {
    @Test
    public void PrepareTest() throws Exception{
        ApiHttpClient.setToken();

    }
}
