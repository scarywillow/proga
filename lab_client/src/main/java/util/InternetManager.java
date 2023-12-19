package util;

import exceptions.NetException;

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class InternetManager {

    private int port;
    InetAddress host;
    public Socket sock;
    public OutputStream os;
    public InputStream is;


    public static InternetManager net;

    public InternetManager(InetAddress host, int port) {
        this.host = host;
        this.port = port;
        this.net = this;
    }

    public static InternetManager get() {
        return net;
    }

    public boolean connect() {
        try {
            sock = new Socket(host, port);
            os = sock.getOutputStream();
            is = sock.getInputStream();
            System.out.println("Connected");
            return true;
        } catch (ConnectException e) {
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Неверно указан порт");
            System.exit(1);
            return false;
        } catch (IOException | NullPointerException e) {
            return false;
        }
    }

    public void send(Request req) throws NetException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(req);
        try {
            os.write(baos.toByteArray());
        } catch (SocketException e) {
            throw new NetException("Ошибка подключения: " + e.getLocalizedMessage());
        } catch (IOException e) {
            throw new NetException("Ошибка IO: " + e.getLocalizedMessage());
        }
    }

    public Response readS() throws ClassNotFoundException {
        try {
            ByteBuffer buf = ByteBuffer.allocate(1024 * 128);
            is.read(buf.array());
            ByteArrayInputStream bais = new ByteArrayInputStream(buf.array());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Response) ois.readObject();
        } catch (java.io.IOException e) {
            IOManager.error("Ошибка чтения");
        }
        return null;
    }


    public void close() throws IOException {
        os.close();
        is.close();
        sock.close();
    }

    public String exchange(Request res) throws NetException, IOException, ClassNotFoundException {
        send(res);
        String msg = ((Response) readS()).getMsg();
        return msg;
    }

    public boolean login() {
        IOManager.out("Введите логин: ");
        String login = IOManager.nextLine().trim();

        try {
            if (net.exchange(new Request("check_user", login)).equals("true")) {
                return checkPwd(login);
            } else {
                IOManager.error("Нет такого юзера");
                login();
            }

        } catch (NetException | IOException | ClassNotFoundException | NullPointerException e) {
        }
        return false;
    }

    public boolean checkPwd(String login) throws NetException, IOException, ClassNotFoundException {
        String pwd = "";
        IOManager.out("Введите пароль: ");
        pwd = IOManager.nextLine().trim();

        Auth.setLogin(login);
        Auth.setPwd(pwd);

        if (net.exchange(new Request("login")).equals("true")) {
            return true;
        } else {
            IOManager.error("Неверный пароль");
            checkPwd(login);
        }
        return false;
    }

    private String getPwd() {
        IOManager.out("Введите пароль: ");
        String pwd = IOManager.nextLine().trim();
        if (pwd.length() >= 4) {
            return pwd;
        } else {
            IOManager.error("В пароле должно быть от 4 символов");
            getPwd();
        }
        return null;
    }


    public boolean addUser(String login, String pwd) {
        Auth.setLogin(login);
        Auth.setPwd(pwd);
        Request req = new Request("register");
        try {
            return net.exchange(req).equals("true");
        } catch (NetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            return false;
        }
        return false;
    }

    public boolean isUserExist(String login) {
        try {
            return net.exchange(new Request("check_user", login)).equals("true");
        } catch (NetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            return false;
        }
        return false;
    }


    public boolean register() {
        IOManager.out("Введите логин: ");
        String login = IOManager.nextLine().trim();
        String pwd;

        if (!isUserExist(login)) {
            pwd = getPwd();
            return addUser(login, pwd);
        } else {
            IOManager.error("Уже есть такой юзер");
            register();
        }
        return false;
    }
}
