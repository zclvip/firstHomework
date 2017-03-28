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
            System.out.println("����ָ������ɫ");
        } catch (IllegalAccessException e) {
            System.out.println("���ඨ�����");
        } catch (ClassNotFoundException e) {
            System.out.println("�Ҳ���ָ��������");;
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
     * ��������ģʽ����һ���ǳ���Ҫ��Ӧ�ã������ӳ�ʼ��(Lazy initialization)��ʲô���ӳ�ʼ���أ�
     * һ�������ʼ����Ϻ�Ͳ��ͷţ��ȵ��ٴ��õ��þͲ����ٴγ�ʼ���ˣ�ֱ�Ӵ��ڴ�����õ��Ϳ�����
     */
    private static HashMap<String,Human> humans = new HashMap<String, Human>();
    public static Human createHuman02(Class c){
        Human human = null;
        try {
            if(humans.containsKey(c.getSimpleName())){
                human = humans.get(c.getSimpleName());
            }else {
                human = (Human)Class.forName(c.getName()).newInstance();
                //�Ž�map��
                humans.put(c.getSimpleName(),human);
            }
        } catch (InstantiationException e) {
            System.out.println("����ָ������ɫ");
        } catch (IllegalAccessException e) {
            System.out.println("���ඨ�����");
        } catch (ClassNotFoundException e) {
            System.out.println("�Ҳ���ָ��������");;
        }
        return human;
    }
}
