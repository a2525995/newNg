package com.qa.api_test.rpc;

public final class ClientFactory {

    private ClientFactory() {}

    public static ClientBase create(EClientType type, ClientConfig config) throws Exception{
            ClientBase client = null;
            switch (type) {
                case THRIFT:
                    client = new ThriftClient(config);
                    break;
            }
            return client;
    }

    public static void main(String[] args) throws Exception{
        ClientConfig conf = new ClientConfig(8080, "localhost");
        ThriftClient thriftClient = (ThriftClient) ClientFactory.create(EClientType.THRIFT, conf);
        thriftClient.start();
    }
}
