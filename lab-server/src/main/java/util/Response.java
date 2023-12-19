package util;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = 322L;
    private String msg;

    public Response(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "Response{" +
                "msg='" + msg + '\'' +
                '}';
    }


}
