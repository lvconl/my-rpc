package edu.lyuconl;

import edu.lyuconl.server.HelloService;
import edu.lyuconl.server.Server;
import edu.lyuconl.server.impl.HelloServiceImpl;
import edu.lyuconl.server.impl.ServceServiceCenter;

import java.io.IOException;

/**
 * @author lyuconl
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Server server = new ServceServiceCenter(9999);
        server.register(HelloService.class, HelloServiceImpl.class);
        server.start();
    }
}
