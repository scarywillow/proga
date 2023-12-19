package util;

import java.nio.channels.SocketChannel;

public class Request2 {
    private Request req;


    private SocketChannel sc;

    public Request2(Request req, SocketChannel sc) {
        this.req = req;
        this.sc = sc;
    }

    public Request getReq() {
        return req;
    }

    public SocketChannel getSc() {
        return sc;
    }

    @Override
    public String toString() {
        return "RequestTask{" +
                "req=" + req +
                ", sc=" + sc +
                '}';
    }
}
