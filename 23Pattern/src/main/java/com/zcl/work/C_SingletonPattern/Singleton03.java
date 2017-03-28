package com.zcl.work.C_SingletonPattern;

/**
 *
 * ����֪ʶ�����£�
 *(1) �����õ������ģʽ����Ĺ��췽��˽�л�������private���Σ���
 *(2) �����ڲ����������ʵ�������󣬲������װ��private static���͡�
 *(3) ����һ����̬�������ظ����ʵ����
 *
 * Created by zhaocl1 on 2017/3/14.
 * ����ģʽ��ʵ�֣�����ʽ,�̰߳�ȫ��ʵ��
 */
public class Singleton03 {

    // ����һ��˽�еĹ��췽�� ��ֹͨ�� new SingletonTest()ȥʵ����
    public Singleton03() {
    }

    // ����ʼ����ע������û��ʹ��final�ؼ���
    private static Singleton03 instance;

    // ʹ��synchronized ������̷߳���ʱ����������صĸ���ʼ�����⣩
    public static synchronized Singleton03 getInstance(){
        if(instance == null){
            instance = new Singleton03();
        }
        return instance;
    }

    /**
     * �ܽ᣺
     * ��˵���еı���ģʽ
     * �ŵ��ǣ�ʹ��synchronized�ؼ��ֱ�����̷߳���ʱ�����ֶ��SingletonTestʵ����
     * ȱ���ǣ�ͬ������Ƶ������ʱ��Ч���Ե͡�
     */
}
