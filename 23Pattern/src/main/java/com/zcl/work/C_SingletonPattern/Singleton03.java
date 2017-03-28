package com.zcl.work.C_SingletonPattern;

/**
 *
 * 核心知识点如下：
 *(1) 将采用单例设计模式的类的构造方法私有化（采用private修饰）。
 *(2) 在其内部产生该类的实例化对象，并将其封装成private static类型。
 *(3) 定义一个静态方法返回该类的实例。
 *
 * Created by zhaocl1 on 2017/3/14.
 * 单例模式的实现：饱汉式,线程安全简单实现
 */
public class Singleton03 {

    // 定义一个私有的构造方法 防止通过 new SingletonTest()去实例化
    public Singleton03() {
    }

    // 不初始化，注意这里没有使用final关键字
    private static Singleton03 instance;

    // 使用synchronized 避免多线程访问时，可能造成重的复初始化问题）
    public static synchronized Singleton03 getInstance(){
        if(instance == null){
            instance = new Singleton03();
        }
        return instance;
    }

    /**
     * 总结：
     * 传说的中的饱汉模式
     * 优点是：使用synchronized关键字避免多线程访问时，出现多个SingletonTest实例。
     * 缺点是：同步方法频繁调用时，效率略低。
     */
}
