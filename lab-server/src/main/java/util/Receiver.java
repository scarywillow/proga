package util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
public class Receiver {
    private InternetManager net;
    private ByteBuffer buf;
    private SocketChannel sc;

    public Receiver (InternetManager net) {
        this.net = net;
        buf = ByteBuffer.allocate(1024 * 128);
    }

    public void init(SocketChannel sc) {
        buf.clear();
        this.sc = sc;
    }

    public boolean isAvailable() throws IOException {
        return sc.read(buf) > 0;
    }

    public Request read(SocketChannel tsc) throws IOException {
        tsc.read(buf);

        Request req = net.readBuf(buf);
        if (req != null) {
            buf.clear();
        }
        return req;
    }
}
