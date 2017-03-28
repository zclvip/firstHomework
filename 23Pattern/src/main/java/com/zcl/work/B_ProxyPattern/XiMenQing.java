package com.zcl.work.B_ProxyPattern;

/**
 * Created by zhaocl1 on 2017/3/14.
 */
public class XiMenQing {

    public static void main(String[] args) {
        //把王婆叫出来
        WangPo wangPo = new WangPo();
        //然后西门庆就说，我要和潘金莲happy，然后王婆就安排了西门庆丢筷子的那出戏:
        wangPo.makeEyesWithMan(); //看到没，虽然表面上时王婆在做，实际上爽的是潘金莲
        wangPo.happyWithMan();

        //改编一下历史，贾氏被西门庆勾走：
        JiaShi jiaShi = new JiaShi();
        WangPo wangPo1 = new WangPo(jiaShi); //让王婆作为贾氏的代理人
        wangPo1.makeEyesWithMan();
        wangPo1.happyWithMan();
    }

    /**
     * 说完这个故事，那额总结一下，代理模式主要使用了Java的多态，干活的是被代理类，代理类主要是接活，你让我干活，好，我交给幕后的类去干，你满意就成，
     * 那怎么知道被代理类能不能干呢？同根就成，大家知根知底，你能做啥，我能做啥都清楚的很，同一个接口呗。
     */
}
