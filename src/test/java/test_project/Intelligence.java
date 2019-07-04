package test_project;

import com.qa.api_test.common.ApiHttpClient;
import com.qa.api_test.propertiesLoader.PropertiesTest;
import com.qa.api_test.yamlParser.TestDataProvider;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Map;

public class Intelligence {

    private String url;
    private TestDataProvider testDataProvider;
    private int clueId;
    private int[] arr = new int[2];

    @BeforeClass
    public void beforeTest() throws Exception{
        PropertiesTest propertiesTest = PropertiesTest.getInstance();
        propertiesTest.loadConfig();
        url = propertiesTest.getUrl();
        ApiHttpClient.setToken();
        testDataProvider = TestDataProvider.getInstance();
        testDataProvider.setMap("intelligence.yml");

    }

    @Test
    public void testTypeList() throws Exception{
        String requestUrl = url + testDataProvider.readApiUrl("get_type_list");
        Map map = ApiHttpClient.GetRequestReturnByMap(requestUrl);
        assert map.get("code").toString().equals("100000");

    }

    @Test
    public void testGetIntelligenceList() throws Exception{
        String requestUrl = url + testDataProvider.readApiUrl("intelligence_list") + testDataProvider.readApiKey("intelligence_list", "pageIndex") + '/' + testDataProvider.readApiKey("intelligence_list", "pageSize");
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formatStr = format.format(new Date());
        String data = testDataProvider.readApiDataByJson("intelligence_list");
        Map m = ApiHttpClient.PostRequestReturnByMap(requestUrl, data);
        String count = m.get("totalRowCount").toString();
        assert (!count.equals("0"));
    }

    @Test
    public void testAddClue() throws Exception{
        String requestUrl = url + testDataProvider.readApiUrl("add_clue");
        String data = testDataProvider.readApiDataByJson("add_clue");
        Map map = ApiHttpClient.PostRequestReturnByMap(requestUrl, data);
        Map m =(Map)map.get("data");
        clueId = (Integer) m.get("id");
        arr[0] = clueId;
        assert clueId != 0;

        data = testDataProvider.readApiKeyByJson("add_clue", "data1");
        map = ApiHttpClient.PostRequestReturnByMap(requestUrl, data);
        m =(Map)map.get("data");
        clueId = (Integer) m.get("id");
        arr[1] = clueId;
        assert clueId != 0;
    }

    @Test
    public void testDispatch() throws Exception{
        String requestUrl = url + testDataProvider.readApiUrl("dispatch");
        String data = testDataProvider.readApiDataByJson("dispatch").replace("0",String.valueOf(clueId));
        Map res = ApiHttpClient.PostRequestReturnByMap(requestUrl, data);
        assert res.get("code").equals("100000");
    }


    @Test
    public void testWatchContent() throws Exception{
        String requestUrl = url + testDataProvider.readApiUrl("watch_content") + clueId;
        Map res = ApiHttpClient.GetRequestReturnByMap(requestUrl);
        assert res.get("code").equals("100000");

    }

    @Test
    public void testExportExcel() throws Exception{
        String requestUrl = url + testDataProvider.readApiUrl("export_excel");
        String data = testDataProvider.readApiDataByJson("export_excel").replace("$token", ApiHttpClient.getToken());
        System.out.println(data);
        byte[] res = ApiHttpClient.PostRequestByFormReceivebytes(requestUrl, data);
        InputStream is = new ByteArrayInputStream(res);
        Workbook wb = WorkbookFactory.create(is);
        Sheet st = wb.getSheetAt(0);

        OutputStream fop = new FileOutputStream(new File("c:/work/testExport.xlsx"));
        wb.write(fop);

//        File f = new File("c:/work/tjjd_exportExcel.xlsx");
        //InputStream inputStream = new ByteArrayInputStream(c);
//        try{
//            FileOutputStream fop = new FileOutputStream(f);
//            OutputStreamWriter writer = new OutputStreamWriter(fop,"UTF-8");
//            for(int i=0;i<res.length;i++){
//                writer.write(res[i]);
//
//            }

//            fop.close();
//            FileInputStream fip = new FileInputStream(f);
//            InputStreamReader reader = new InputStreamReader(fip,"UTF-8");
//            StringBuffer sb = new StringBuffer();
//            while(reader.ready()){
//                sb.append((char)reader.read());
            }
//            System.out.println("******************************************result = "+sb.toString());
//            fip.close();

//        }

//


    @AfterClass
    public void afterTest() throws Exception {
            String requestUrl = url + testDataProvider.readApiUrl("delete_message");
            String data = testDataProvider.readApiDataByJson("delete_message").replace("0", Arrays.toString(arr));
            Map res = ApiHttpClient.PostRequestReturnByMap(requestUrl, data);
            assert res.get("code").equals("100000") && (!res.get("data").equals(0));
    }
}
