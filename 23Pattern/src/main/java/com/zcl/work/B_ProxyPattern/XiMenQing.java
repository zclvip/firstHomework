package com.zcl.work.B_ProxyPattern;

/**
 * Created by zhaocl1 on 2017/3/14.
 */
public class XiMenQing {

    public static void main(String[] args) {
        //�����Žг���
        WangPo wangPo = new WangPo();
        //Ȼ���������˵����Ҫ���˽���happy��Ȼ�����žͰ����������춪���ӵ��ǳ�Ϸ:
        wangPo.makeEyesWithMan(); //����û����Ȼ������ʱ����������ʵ����ˬ�����˽���
        wangPo.happyWithMan();

        //�ı�һ����ʷ�����ϱ������카�ߣ�
        JiaShi jiaShi = new JiaShi();
        WangPo wangPo1 = new WangPo(jiaShi); //��������Ϊ���ϵĴ�����
        wangPo1.makeEyesWithMan();
        wangPo1.happyWithMan();
    }

    /**
     * ˵��������£��Ƕ��ܽ�һ�£�����ģʽ��Ҫʹ����Java�Ķ�̬���ɻ���Ǳ������࣬��������Ҫ�ǽӻ�����Ҹɻ�ã��ҽ���Ļ�����ȥ�ɣ�������ͳɣ�
     * ����ô֪�����������ܲ��ܸ��أ�ͬ���ͳɣ����֪��֪�ף�������ɶ��������ɶ������ĺܣ�ͬһ���ӿ��¡�
     */
}
