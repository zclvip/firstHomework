package com.zcl.work.B_ProxyPattern;

/**
 * Created by zhaocl1 on 2017/3/14.
 *
 * ����������ϴ����ˣ���̫���ˣ��Ǹ����˶������ϣ�
 * ���������ǻ��о���ѽ������Ϊһ��Ů�˵Ĵ���
 */
public class WangPo implements KindWomen {

    private KindWomen kindWomen;

    //Ĭ�ϵĻ������˽����Ĵ���
    public WangPo() {
        this.kindWomen = new PanJinLian();
    }

    //��������KindWomen���κ�һ��Ů�˵Ĵ���ֻҪ������һ����
    public WangPo(KindWomen kindWomen) {
        this.kindWomen = kindWomen;
    }

    public void makeEyesWithMan() {
        this.kindWomen.happyWithMan(); //�Լ����ˣ��ɲ��ˣ�����������Ĵ���
    }

    public void happyWithMan() {
        this.kindWomen.makeEyesWithMan(); //������ô�������ˣ�˭���������ۣ���
    }
}
