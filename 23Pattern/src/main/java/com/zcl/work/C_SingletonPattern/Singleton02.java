package com.zcl.work.C_SingletonPattern;

/**
 *
 * 核心知识点如下：
 *(1) 将采用单例设计模式的类的构造方法私有化（采用private修饰）。
 *(2) 在其内部产生该类的实例化对象，并将其封装成private static类型。
 *(3) 定义一个静态方法返回该类的实例。
 *
 * Created by zhaocl1 on 2017/3/14.
 * 单例模式的实现：饱汉式,非线程安全
 */
public class Singleton02 {

    // 定义一个私有的构造方法 防止通过 new SingletonTest()去实例化
    public Singleton02() {
    }

    // 不初始化，注意这里没有使用final关键字
    private static  Singleton02 instance;

    // 多线程访问时，可能造成重复初始化问题
    public static Singleton02 getInstance(){
        if(instance == null){
            instance = new Singleton02();
        }
        return instance;
    }

    /**
     * 总结：
     * 传说的中的饱汉模式
     * 优点是：写起来比较简单，当类SingletonTest被加载的时候，静态变量static的instance未被创建并分配内存空间，当getInstance方法第一次被调用时，初始化instance变量，并分配内存，因此在某些特定条件下会节约了内存；
     * 缺点是：并发环境下很可能出现多个SingletonTest实例。
     */
}
