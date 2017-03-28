package com.zcl.work.G_AdapterPattern;

import java.util.Map;

/**
 * Created by zhaocl1 on 2017/3/17.
 */

public class OuterUserInfo extends OuterUser implements IUserInfo {

    private Map baseInfo = super.getUserBaseInfo(); //Ա���Ļ�����Ϣ
    private Map homeInfo = super.getUserHomeInfo(); //Ա���ļ�ͥ��Ϣ
    private Map officeInfo = super.getUserOfficeInfo(); //������Ϣ

    public String getHomeTelNumber() {
        String homeTelNumber = (String)this.homeInfo.get("homeTelNumber");
        System.out.println(homeTelNumber);
        return homeTelNumber;
    }
    /*
    *ְλ��Ϣ
    */
    public String getJobPosition() {
        String jobPosition = (String)this.officeInfo.get("jobPosition");
        System.out.println(jobPosition);
        return jobPosition;
    }
    /*
    * �ֻ�����
    */
    public String getMobileNumber() {
        String mobileNumber = (String)this.baseInfo.get("mobileNumber");
        System.out.println(mobileNumber);
        return mobileNumber;
    }

    public String getOfficeTelNumber() {
        String officeTelNumber = (String)this.officeInfo.get("officeTelNumber");
        System.out.println(officeTelNumber);
        return officeTelNumber;
    }
    /*
    * Ա��������
    */
    public String getUserName() {
        String userName = (String)this.baseInfo.get("userName");
        System.out.println(userName);
        return userName;
    }

    public String getHomeAddress() {
        return null;
    }
}
