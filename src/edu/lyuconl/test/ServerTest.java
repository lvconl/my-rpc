package edu.lyuconl.test;

import edu.lyuconl.server.HelloService;
import edu.lyuconl.server.Server;
import edu.lyuconl.server.impl.HelloServiceImpl;
import edu.lyuconl.server.impl.ServceServiceCenter;

import java.io.IOException;

/**
 * @author lyuconl
 * 服务端测试类
 */
public class ServerTest {
    public static void main(String[] args) throws IOException {
        Server server = new ServceServiceCenter(9999);
        server.register(HelloService.class, HelloServiceImpl.class);
        server.start();
    }
}
