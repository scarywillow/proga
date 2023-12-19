import java.io.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.*;

import util.*;

public class Server {

    private static final ForkJoinPool recPool = new ForkJoinPool(4);
    private static final ExecutorService procPool = Executors.newCachedThreadPool();
    private static final ForkJoinPool sendPool = new ForkJoinPool(4);

    volatile static List<Request2> requests = new ArrayList<>();
    volatile static List<Response2> responses = new ArrayList<>();


    public static void main(String[] args) throws IOException{

        InternetManager net = null;
        try {
            net = new InternetManager(Integer.parseInt(args[0]));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Неверно указан порт");
            System.exit(1);
        }
        net.init();
        /*FileManager fileManager;*/
        CommandManager commandManager = null;
        CollectionManager collectionManager = null;

        try {
            /* fileManager = new FileManager(args[0]); //args[0] */
            collectionManager = new CollectionManager();
            commandManager = new CommandManager(collectionManager);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Файл не найден");
            System.exit(1);
        }

        CommandManager finalCommandManager = commandManager;

        new Thread(() -> {
            while (true) {
                while (requests.iterator().hasNext()) {
                    Request2 rt = requests.iterator().next();
                    procPool.submit(() -> {
                        String res = finalCommandManager.runPrepared(rt.getReq());
                        Response resp = new Response(res);
                        responses.add(new Response2(resp, rt.getSc()));
                    });
                    requests.remove(rt);
                }

                while (responses.iterator().hasNext()) {
                    Response2 respt = responses.iterator().next();
                    sendPool.submit(() -> {
                        Sender.send(respt.getResp(), respt.getSc());
                    });
                    responses.remove(respt);
                }
            }
        }).start();


        while (true) {
            try {
                net.getSelector().select();
            } catch (IOException e) {
            }
            Set keys = net.getSelector().selectedKeys();
            Iterator i = keys.iterator();
            while (i.hasNext()) {
                SelectionKey selectionKey = (SelectionKey) i.next();
                i.remove();
                Accepter acc = new Accepter(net);
                try {
                    acc.accept(selectionKey);
                } catch (IOException e) {
                }
                Receiver receiver = new Receiver(net);

                if (selectionKey.isReadable()) {
                    SocketChannel sc = (SocketChannel) selectionKey.channel();
                    receiver.init(sc);

                    recPool.invoke(new RecursiveAction() {
                        @Override
                        protected void compute() {
                            while (true) {
                                try {
                                    Request tmpReq = receiver.read(sc);
                                    if (tmpReq != null) {
                                        requests.add(new Request2(tmpReq, sc));
                                    }
                                } catch (NullPointerException | IOException e) {
                                    break;
                                }
                            }
                        }
                    });
                }
            }
        }
    }
}
