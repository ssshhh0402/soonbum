package com.example.hong.dhproject3;

public class allAlertInfo {
    String d_name,alertDate,m_name,id, d_id;
    int alertStatus;

    public allAlertInfo(String master, String d_name, int alertStatus, String alertDate, String id, String d_id) {
        this.d_name = d_name;
        this.alertStatus = alertStatus;
        this.alertDate = alertDate;
        this.m_name = master;
        this.id= id;
        this.d_id= d_id;
    }

    public  String getAlertStatus() {

        if(this.alertStatus == 1)
        return "비상";
        else if(this.alertStatus == 2)
            return "이산화탄소";
        else if(this.alertStatus == 3)
            return "온도초과";
        else if(this.alertStatus == 4)
            return "화재발생";
        else if(this.alertStatus == 5)
            return "흔들림감지";
        else if(this.alertStatus == 6)
            return "서버연결";
        else
            return "오류가 발생했습니다";//return alertStatus;
    }

    public void setId(String a){
        this.id = a;
    }

    public String getId(){
        return this.id;
    }
    public void setAlertStatus(int alertStatus) {
        this.alertStatus = alertStatus;
    }

    public String getM_name(){
        return this.m_name;
    }

    public void setM_name(String a){
        this.m_name = a;
    }
    public String getD_name(){
        return this.d_name;
    }

    public void setD_name(String a){
        this.d_name = a;
    }
    public String getAlertDate() {
        String a = this.alertDate.substring(0,10);
       // String b = this.alertDate.substring(11,19);

        return a;
    }

    public String getD_id(){
        return this.d_id;
    }

    public void setD_id(String a){
        this.d_id = a;
    }
    public String getAlertTime(){
        String a = this.alertDate.substring(11, 19);
        return a;
    }

    public void setAlertDate(String alertDate) {
        this.alertDate = alertDate;
    }
}
