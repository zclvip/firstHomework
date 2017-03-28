package com.zcl.work.G_AdapterPattern;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaocl1 on 2017/3/17.
 */
public class OuterUser implements IOuterUser {
    public Map getUserBaseInfo() {
        HashMap baseInfoMap = new HashMap();
        baseInfoMap.put("userName", "���Ա���л���ħ��....");
        baseInfoMap.put("mobileNumber", "���Ա���绰��....");
        return baseInfoMap;
    }
    /*
    * Ա���ļ�ͥ��Ϣ
    */
    public Map getUserHomeInfo() {
        HashMap homeInfo = new HashMap();
        homeInfo.put("homeTelNumbner", "Ա���ļ�ͥ�绰��....");
        homeInfo.put("homeAddress", "Ա���ļ�ͥ��ַ��....");
        return homeInfo;
    }
    /*
    * Ա���Ĺ�����Ϣ������ְλ�˵�
    */
    public Map getUserOfficeInfo() {
        HashMap officeInfo = new HashMap();
        officeInfo.put("jobPosition","����˵�ְλ��BOSS...");
        officeInfo.put("officeTelNumber", "Ա���İ칫�绰��....");
        return officeInfo;
    }
}
