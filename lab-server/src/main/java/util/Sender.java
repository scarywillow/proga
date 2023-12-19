package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Sender {
    public static void send(Response resp, SocketChannel sc) {
        ByteArrayOutputStream baos;
        ObjectOutputStream oos;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(resp);
            sc.write(ByteBuffer.wrap(baos.toByteArray()));
        } catch (IOException e) {
        }
    }
}
