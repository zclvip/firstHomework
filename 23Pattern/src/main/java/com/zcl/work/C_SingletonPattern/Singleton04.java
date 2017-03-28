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
public class Singleton04 {

    // ����һ��˽�еĹ��췽�� ��ֹͨ�� new SingletonTest()ȥʵ����
    public Singleton04() {
    }

    /**
     * ����һ����̬˽�б���(����ʼ������ʹ��final�ؼ��֣�ʹ��volatile��֤�˶��̷߳���ʱinstance�����Ŀɼ��ԣ�
     * ������instance��ʼ��ʱ�����������Ի�û��ֵ��ʱ���������̵߳���)
     */
    private static volatile Singleton04 instance;

    // ����һ�����еľ�̬���������ظ�����ʵ��
    public static Singleton04 getInstance(){
        //����ʵ����ʱ����жϣ���ʹ��ͬ������飬instance������nullʱ��ֱ�ӷ��ض����������Ч�ʣ�
        if(instance == null){
            //ͬ������飨����δ��ʼ��ʱ��ʹ��ͬ������飬��֤���̷߳���ʱ�����ڵ�һ�δ����󣬲����ظ���������
            synchronized (Singleton04.class){
                //δ��ʼ�������ʼinstance����
                if(instance == null){
                    instance = new Singleton04();
                }
            }
        }
        return instance;
    }

    /**
     * �ܽ᣺
     * ������Ϊ����ģʽ�����ʵ�֡��ڴ�ռ�õأ�Ч�ʸߣ��̰߳�ȫ�����̲߳���ԭ���ԡ�
     *
     * ��ʵ�ϣ�����ͨ��Java���������ʵ����private���͵Ĺ��췽������ʱ�����ϻ�ʹ���е�Java����ʵ��ʧЧ
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
             * ������Ϊ��
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
