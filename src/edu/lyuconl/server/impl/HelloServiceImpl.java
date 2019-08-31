package edu.lyuconl.server.impl;

import edu.lyuconl.server.HelloService;

/**
 * @author lyuconl
 * 类说明:HelloService接口实现类
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHi(String name) {
        System.out.println("hello" + name);
    }
}
