package com.zcl.work.G_AdapterPattern;

import java.util.Map;

/**
 * Created by zhaocl1 on 2017/3/17.
 */
public interface IOuterUser {
    //������Ϣ���������ƣ��Ա��ֻ������˵�
    public Map getUserBaseInfo();
    //����������Ϣ
    public Map getUserOfficeInfo();
    //�û��ļ�ͥ��Ϣ
    public Map getUserHomeInfo();
}
