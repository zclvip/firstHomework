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
public class Singleton04 {

    // 定义一个私有的构造方法 防止通过 new SingletonTest()去实例化
    public Singleton04() {
    }

    /**
     * 定义一个静态私有变量(不初始化，不使用final关键字，使用volatile保证了多线程访问时instance变量的可见性，
     * 避免了instance初始化时其他变量属性还没赋值完时，被另外线程调用)
     */
    private static volatile Singleton04 instance;

    // 定义一个共有的静态方法，返回该类型实例
    public static Singleton04 getInstance(){
        //对象实例化时与否判断（不使用同步代码块，instance不等于null时，直接返回对象，提高运行效率）
        if(instance == null){
            //同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
            synchronized (Singleton04.class){
                //未初始化，则初始instance变量
                if(instance == null){
                    instance = new Singleton04();
                }
            }
        }
        return instance;
    }

    /**
     * 总结：
     * 方法四为单例模式的最佳实现。内存占用地，效率高，线程安全，多线程操作原子性。
     *
     * 事实上，可以通过Java反射机制来实例化private类型的构造方法，此时基本上会使所有的Java单例实现失效
     */

    public static void main(String[] args){

        try {
            Singleton04 instance01 = Singleton04.getInstance();
            Singleton04 instance02 = Singleton04.class.newInstance();
            Singleton04 instance03 = Singleton04.getInstance();
            System.out.println("instance01="+instance01);
            System.out.println("instance02="+instance02);
            System.out.println("instance03="+instance03);


            /**
             * 输出结果为：
             * instance01=com.zcl.work.C_SingletonPattern.Singleton04@42bdfa0e
             * instance02=com.zcl.work.C_SingletonPattern.Singleton04@466bcf5d
             * instance03=com.zcl.work.C_SingletonPattern.Singleton04@42bdfa0e
             */
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
