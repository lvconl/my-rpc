package edu.lyuconl.server;

import java.io.IOException;

/**
 * @author lyuconl
 * 类说明：服务端服务中心
 */
public interface Server {
    /**
     * 服务启动
     */
    void start() throws IOException;

    /**
     * 服务停止
     */
    void stop();

    /**
     * 服务具体实现
     * @param service 注册服务
     * @param serviceImpl 注册服务实现类
     */
    void register(Class service, Class serviceImpl);
}
