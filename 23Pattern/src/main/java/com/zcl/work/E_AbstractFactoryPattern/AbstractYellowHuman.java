package com.zcl.work.E_AbstractFactoryPattern;

/**
 * Created by zhaocl1 on 2017/3/16.
 */
public abstract class AbstractYellowHuman  implements Human {
    public void cry() {
        System.out.println("��ɫ�����");
    }
    public void laugh() {
        System.out.println("��ɫ����Ц");
    }
    public void talk() {
        System.out.println("��ɫ����˵��");
    }
}
