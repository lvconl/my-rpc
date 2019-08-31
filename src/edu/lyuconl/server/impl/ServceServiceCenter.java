package edu.lyuconl.server.impl;

import edu.lyuconl.server.Server;
import sun.awt.windows.ThemeReader;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServceServiceCenter implements Server {

    private static HashMap<Class, Class> serviceRegister =  new HashMap<Class, Class>();

    private int port;

    public ServceServiceCenter(int port) {
        this.port = port;
    }

    @Override
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(port));
        Runnable runnable = () -> {
            //阻塞等待客户端连接
            System.out.println("服务启动，等待客户端连接...");
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /**
             * 接受客户端请求，处理请求
             * 因为数据为按顺序传入，所有此处接收数据为顺序接收
             */
            ObjectInputStream input = null;
            try {
                input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String serviceName = null;
            try {
                serviceName = input.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String methodName = null;
            try {
                methodName = input.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Class[] parameterTypes = null;
            Object[] args = null;
            try {
                parameterTypes = (Class[]) input.readObject();
                args = (Object [])input.readObject();
            } catch (ClassNotFoundException | IOException e) {
            } finally {
                assert parameterTypes != null;
                assert args != null;
            }

            /**
             * 根据请求，找到具体接口并执行相关方法
             */
            System.out.println(serviceRegister);
            Class serviceClass = null;
            try {
                serviceClass = Class.forName(serviceName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Method method = null;
            try {
                method = serviceClass.getMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            assert method != null;
            Object result = null;
            try {
                result = method.invoke(serviceClass.newInstance(), args);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            /**
             * 执行结束，将结果返回至客户端
             */
            assert result != null;
            ObjectOutputStream output = null;
            try {
                output = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                output.writeObject(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread t = new Thread(runnable);
        t.start();
    }

    @Override
    public void stop() {

    }

    @Override
    public void register(Class service, Class serviceImpl) {
        serviceRegister.put(service, serviceImpl);
    }

}
