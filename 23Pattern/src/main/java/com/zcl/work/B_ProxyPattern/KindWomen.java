package com.zcl.work.B_ProxyPattern;

/**
 * 什么是代理模式呢？我很忙，忙的没空理你，那你要找我呢就先找我的代理人吧，那代理人总要知道被代理人能做哪些事情不能做哪些事情吧，
 * 那就是两个人具备同一个接口，代理人虽然不能干活，但是被代理的人能干活呀。
 *
 *比如西门庆找潘金莲，那潘金莲不好意思答复呀，咋办，找那个王婆做代理，表现在程序上时这样的：
 *先定义一种类型的女人：
 *
 * Created by zhaocl1 on 2017/3/14.
 *
 * 定义一种类型的女人，王婆和潘金莲都属于这个类型的女人
 */
public interface KindWomen {

    //这种类型的女人能做什么事情呢？
    public void makeEyesWithMan(); //抛媚眼
    public void happyWithMan(); //happy what? You know that!
}
