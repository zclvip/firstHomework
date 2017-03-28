package com.zcl.work.C_SingletonPattern;

/**
 *
 * ����֪ʶ�����£�
 *(1) �����õ������ģʽ����Ĺ��췽��˽�л�������private���Σ���
 *(2) �����ڲ����������ʵ�������󣬲������װ��private static���͡�
 *(3) ����һ����̬�������ظ����ʵ����
 *
 * Created by zhaocl1 on 2017/3/14.
 * ����ģʽ��ʵ�֣�����ʽ,���̰߳�ȫ
 */
public class Singleton02 {

    // ����һ��˽�еĹ��췽�� ��ֹͨ�� new SingletonTest()ȥʵ����
    public Singleton02() {
    }

    // ����ʼ����ע������û��ʹ��final�ؼ���
    private static  Singleton02 instance;

    // ���̷߳���ʱ����������ظ���ʼ������
    public static Singleton02 getInstance(){
        if(instance == null){
            instance = new Singleton02();
        }
        return instance;
    }

    /**
     * �ܽ᣺
     * ��˵���еı���ģʽ
     * �ŵ��ǣ�д�����Ƚϼ򵥣�����SingletonTest�����ص�ʱ�򣬾�̬����static��instanceδ�������������ڴ�ռ䣬��getInstance������һ�α�����ʱ����ʼ��instance�������������ڴ棬�����ĳЩ�ض������»��Լ���ڴ棻
     * ȱ���ǣ����������ºܿ��ܳ��ֶ��SingletonTestʵ����
     */
}
