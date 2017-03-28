package com.zcl.work.C_SingletonPattern;

/**
 *
 * 核心知识点如下：
 *(1) 将采用单例设计模式的类的构造方法私有化（采用private修饰）。
 *(2) 在其内部产生该类的实例化对象，并将其封装成private static类型。
 *(3) 定义一个静态方法返回该类的实例。
 *
 * Created by zhaocl1 on 2017/3/14.
 * 单例模式的实现：饿汉式,线程安全 但效率比较低
 */
public class Singleton01 {

    // 定义一个私有的构造方法
    public Singleton01() {
    }

    // 将自身的实例对象设置为一个属性,并加上Static和final修饰符
    private static final Singleton01 instance = new Singleton01();

    // 静态方法返回该类的实例
    public static Singleton01 getInstance(){
        return instance;
    }

    /**
     * 总结：
     * 优点是：写起来比较简单，而且不存在多线程同步问题，避免了synchronized所造成的性能问题；
     * 缺点是：当类SingletonTest被加载的时候，会初始化static的instance，静态变量被创建并分配内存空间，
     * 从这以后，这个static的instance对象便一直占着这段内存（即便你还没有用到这个实例），当类被卸载时，静态变量被摧毁，并释放所占有的内存，
     * 因此在某些特定条件下会耗费内存。
     */
}
