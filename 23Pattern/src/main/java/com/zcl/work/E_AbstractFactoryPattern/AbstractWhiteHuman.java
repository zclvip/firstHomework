package com.zcl.work.E_AbstractFactoryPattern;

/**
 * Created by zhaocl1 on 2017/3/16.
 */
public abstract class AbstractWhiteHuman implements Human{
    public void cry() {
        System.out.println("���˿�");
    }
    public void laugh() {
        System.out.println("����Ц");
    }
    public void talk() {
        System.out.println("����˵��");
    }
}
