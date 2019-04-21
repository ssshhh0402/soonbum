package com.example.hong.dhproject3;

public class Devices {
    int deviceId;
    String deviceName,deviceNick,userType,u_id,d_code,master;
    Boolean aAlarm,bAlarm,cAlarm;


    public Devices(String u_id, int deviceId, String deviceName, String deviceNick, String userType, String d_code, String aMaster, Boolean aAlarm, Boolean bAlarm, Boolean cAlarm) {
        this.u_id = u_id;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceNick = deviceNick;
        this.userType = userType;
        this.d_code = d_code;
        this.master = aMaster;
        this.aAlarm = aAlarm;
        this.bAlarm = bAlarm;
        this.cAlarm = cAlarm;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getDeviceId() {
        return deviceId;
    }


    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceNick(){
        return deviceNick;
    }

    public void setDeviceNick(String a){
        this.deviceNick = a;
    }
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getD_code() {
        return d_code;
    }

    public void setD_code(String d_code) {
        this.d_code = d_code;
    }

    public String get_master(){
        return master;
    }

    public void set_master(String a){
        this.master = a;
    }

    public Boolean getaAlarm() {
        return aAlarm;
    }

    public void setaAlarm(Boolean aAlarm) {
        this.aAlarm = aAlarm;
    }

    public Boolean getbAlarm() {
        return bAlarm;
    }

    public void setbAlarm(Boolean bAlarm) {
        this.bAlarm = bAlarm;
    }

    public Boolean getcAlarm() {
        return cAlarm;
    }

    public void setcAlarm(Boolean cAlarm) {
        this.cAlarm = cAlarm;
    }
}
