package com.example.hong.dhproject3;

public class alertInfo {

    int alertStatus;
    String alertDate;
    public alertInfo(int alertStatus, String alertDate) {
        this.alertStatus = alertStatus;
        this.alertDate = alertDate;
    }

    public /*int*/ String getAlertStatus() {

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

    public void setAlertStatus(int alertStatus) {
        this.alertStatus = alertStatus;
    }

    public String getAlertDate() {
        String a = this.alertDate.substring(0,10);
       // String b = this.alertDate.substring(11,19);

        return a;
    }
    public String getAlertTime(){
        String a = this.alertDate.substring(11, 19);
        return a;
    }

    public void setAlertDate(String alertDate) {
        this.alertDate = alertDate;
    }
}
