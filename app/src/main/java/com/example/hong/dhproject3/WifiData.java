package com.example.hong.dhproject3;

public class WifiData {
    private String BSSID;
    private String SSID;
    private boolean isOn;

    public WifiData(String BSSID, String SSID, boolean isOn){
        this.BSSID = BSSID;
        this.SSID = SSID;
        this.isOn = isOn;
    }

    public String getBSSID(){
        return BSSID;
    }
    public void setBSSID(String BSSID){
        this.BSSID = BSSID;
    }

    public String getSSID(){
        return SSID;
    }
    public void setSSID(String SSID){
        this.SSID = SSID;
    }

    public boolean isOn(){
        return isOn;
    }
    public void setOn(boolean on){
        isOn = on;
    }
}
