package com.qa.api_test.rpc;

import com.sophon.call.mining.rpc.CallAnalysis;
import com.sophon.call.mining.rpc.CallDetailInfo;
import com.sophon.call.mining.rpc.CallMiningService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.Map;

public class ThriftClient extends ClientBase {


    public String host;
    public int port;
    private TTransport transport;
    private CallMiningService.Client client;

    public Map<String, CallAnalysis> testFetchCallAnalysis(String phoneNumber, long startTime, long endTime) throws TException {

        Map<String, CallAnalysis> map = client.fetchCallAnalysis(phoneNumber, startTime, endTime);
        return map;
    }

    public CallDetailInfo testFetchCallDetailInfo(String phoneNumber, int pageIndex, int pageSize) throws TException {
        CallDetailInfo callDetailInfo = client.fetchCallDetailInfo(phoneNumber, pageIndex, pageSize);
        return callDetailInfo;
    }


    public ThriftClient(ClientConfig config) {
        this.setHost(config.getHost());
        this.setPort(config.getPort());

    }

    @Override
    public void shutDown() {
        if (transport.isOpen()) {
            transport.close();
        }
    }

    @Override
    public void start() {
        transport = new TFramedTransport(new TSocket(host, port));
        try {
            transport.open();

        } catch (TException e) {
            e.printStackTrace();
        }
        TProtocol protocol = new TBinaryProtocol(transport);
        this.client = new CallMiningService.Client(protocol);

    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


}
