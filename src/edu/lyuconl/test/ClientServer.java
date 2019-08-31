package edu.lyuconl.test;

import edu.lyuconl.client.Client;
import edu.lyuconl.server.HelloService;

import java.net.InetSocketAddress;

/**
 * @author lyuconl
 * 客户端测试类
 */
public class ClientServer {
    public static void main(String[] args) throws ClassNotFoundException {
        HelloService service = Client.getRemoteProxyObj(Class.forName("edu.lyuconl.server.HelloService"),
                new InetSocketAddress("127.0.0.1", 9999));
        service.sayHi("zs");
    }
}
