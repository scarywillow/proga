package util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;
import static java.nio.channels.SelectionKey.OP_ACCEPT;

public class InternetManager {
    private final int port;
    private ServerSocketChannel ssc;
    private SocketChannel sc;
    private Selector selector;


    public InternetManager(int port) {
        this.port = port;
    }

    public Selector getSelector() {
        return selector;
    }

    public void init() {
        try {
            InetSocketAddress addr = new InetSocketAddress(port);
            ssc = ServerSocketChannel.open();
            ssc.bind(addr);
            ssc.configureBlocking(false);
            selector = Selector.open();
            ssc.register(selector, OP_ACCEPT);
        } catch (BindException e) {
            System.out.println("Неверно указан порт");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Request readBuf(ByteBuffer buf) {
        try {
            if (buf.position() != 0) {
                ByteArrayInputStream bais = new ByteArrayInputStream(buf.array());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Request request = (Request) ois.readObject();
                return request;
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SocketChannel accept(SelectionKey sk) {
        try {
            sc = ssc.accept();
        } catch (IOException e) {
            e.printStackTrace();
            sk.cancel();
        }
        if (sc != null) {
            try {
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
                sk.cancel();
            }
        }
        return sc;
    }

    public SocketChannel forceAccept() {
        try {
            sc = ssc.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (sc != null) {
            try {
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sc;
    }
}
