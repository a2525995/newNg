package com.qa.api_test.rpc;

public class ClientConfig {

    private String host;
    private int port;



    public ClientConfig(int port, String host){
        this.port = port;
        this.host = host;
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

    public String toString(){
        return String.format("host = %s, port = %d", host, port);
    }

}
