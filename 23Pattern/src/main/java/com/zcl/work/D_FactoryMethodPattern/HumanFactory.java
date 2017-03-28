package com.zcl.work.D_FactoryMethodPattern;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by zhaocl1 on 2017/3/16.
 */
public class HumanFactory {

    public static Human createHuman(Class c){
        Human human = null;
        try {
            human = (Human)Class.forName(c.getName()).newInstance();
        } catch (InstantiationException e) {
            System.out.println("必须指定人颜色");
        } catch (IllegalAccessException e) {
            System.out.println("人类定义错误！");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到指定的人类");;
        }
        return human;
    }

    public static Human createHuman(){
        Human human = null;
        List<Class> concreteHumanList = ClassUtils.getAllClassByInterface(Human.class);
        Random random = new Random();
        int rand = random.nextInt(concreteHumanList.size());
        human= createHuman(concreteHumanList.get(rand));
        return human;
    }

    /**
     * 工厂方法模式还有一个非常重要的应用，就是延迟始化(Lazy initialization)，什么是延迟始化呢？
     * 一个对象初始化完毕后就不释放，等到再次用到得就不用再次初始化了，直接从内存过中拿到就可以了
     */
    private static HashMap<String,Human> humans = new HashMap<String, Human>();
    public static Human createHuman02(Class c){
        Human human = null;
        try {
            if(humans.containsKey(c.getSimpleName())){
                human = humans.get(c.getSimpleName());
            }else {
                human = (Human)Class.forName(c.getName()).newInstance();
                //放进map中
                humans.put(c.getSimpleName(),human);
            }
        } catch (InstantiationException e) {
            System.out.println("必须指定人颜色");
        } catch (IllegalAccessException e) {
            System.out.println("人类定义错误！");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到指定的人类");;
        }
        return human;
    }
}
