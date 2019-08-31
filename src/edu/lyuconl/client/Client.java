package edu.lyuconl.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author lyuconl
 * 客户端
 */
public class Client {
    /**
     * 获取代表服务端接口的动态代理对象（HelloService）
     * @param serviceName 接口名
     * @param addr 服务端地址
     * @param <T> 接口类型
     * @return 代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getRemoteProxyObj(Class serviceName, InetSocketAddress addr) {
        /**
         * a:类加载器：需要代理哪个类，就需将类的类加载器传入方法
         * b：需要代理的对象，具备什么方法--> say(), sleep()等，由于Java多实现机制，所以传入接口数组
         *
         */
        return (T) Proxy.newProxyInstance(serviceName.getClassLoader(),
                new Class<?>[] {serviceName},
                new InvocationHandler() {
                    /**
                     * 指定返回的代理对象的工作
                     * @param proxy 代理对象本身
                     * @param method 代理对象当前的方法
                     * @param args 方法参数
                     * @return 执行结果
                     * @throws Throwable
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Socket socket = new Socket();
                        socket.connect(addr);
                        /**
                         * 序列化发送数据
                         */
                        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                        output.writeUTF(serviceName.getName());
                        output.writeUTF(method.getName());
                        output.writeObject(method.getParameterTypes());
                        output.writeObject(args);
                        /**
                         * 接收服务端执行结果
                         */
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        return input.readObject();
                    }
                });
    }
}
