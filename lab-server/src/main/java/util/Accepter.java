package util;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.logging.Logger;

public class Accepter {
    private InternetManager net;

    public Accepter(InternetManager net) {
        this.net = net;
    }

    public void accept(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isAcceptable()) {
            String adr = net.accept(selectionKey).getRemoteAddress().toString();
        }
    }
}
