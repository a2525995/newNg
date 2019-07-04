package thrift;

import com.qa.api_test.propertiesLoader.PropertiesThrift;
import com.qa.api_test.rpc.ClientConfig;
import com.qa.api_test.rpc.ClientFactory;
import com.qa.api_test.rpc.EClientType;
import com.qa.api_test.rpc.ThriftClient;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ThriftClientTest {

    private String url;

    private ThriftClient thriftClient;

    @BeforeClass
    public void beforetest() throws FileNotFoundException, IOException , Exception{
        PropertiesThrift.loadConfig();
        ClientConfig clientConfig = new ClientConfig(PropertiesThrift.GetPort(), PropertiesThrift.getHost());
        this.thriftClient = (ThriftClient) ClientFactory.create(EClientType.THRIFT, clientConfig);
        thriftClient.start();
        }


//    @Test
//    public void testFetchCallInfo() throws TException {
//        System.out.println(thriftClient.testFetchCallAnalysis("13333333333", 1555555555557777l, 1558355160000L));
//        assert thriftClient.testFetchCallAnalysis("13333333333", 1L, 1558355160000L).isEmpty() ;
//    }

//    @Test
//    public void testFetchCallDetailInfo() throws TException{
//        System.out.println(thriftClient.testFetchCallDetailInfo("13333333333", 20, 20));
//        assert thriftClient.testFetchCallDetailInfo("13333333333", 20, 20);
//
//    }

    @AfterClass
    public void aftertest(){
        thriftClient.shutDown();
        thriftClient = null;
    }




}
