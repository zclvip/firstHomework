package com.zcl.work.C_SingletonPattern;

/**
 *
 * ����֪ʶ�����£�
 *(1) �����õ������ģʽ����Ĺ��췽��˽�л�������private���Σ���
 *(2) �����ڲ����������ʵ�������󣬲������װ��private static���͡�
 *(3) ����һ����̬�������ظ����ʵ����
 *
 * Created by zhaocl1 on 2017/3/14.
 * ����ģʽ��ʵ�֣�����ʽ,�̰߳�ȫ ��Ч�ʱȽϵ�
 */
public class Singleton01 {

    // ����һ��˽�еĹ��췽��
    public Singleton01() {
    }

    // �������ʵ����������Ϊһ������,������Static��final���η�
    private static final Singleton01 instance = new Singleton01();

    // ��̬�������ظ����ʵ��
    public static Singleton01 getInstance(){
        return instance;
    }

    /**
     * �ܽ᣺
     * �ŵ��ǣ�д�����Ƚϼ򵥣����Ҳ����ڶ��߳�ͬ�����⣬������synchronized����ɵ��������⣻
     * ȱ���ǣ�����SingletonTest�����ص�ʱ�򣬻��ʼ��static��instance����̬�����������������ڴ�ռ䣬
     * �����Ժ����static��instance�����һֱռ������ڴ棨�����㻹û���õ����ʵ���������౻ж��ʱ����̬�������ݻ٣����ͷ���ռ�е��ڴ棬
     * �����ĳЩ�ض������»�ķ��ڴ档
     */
}
