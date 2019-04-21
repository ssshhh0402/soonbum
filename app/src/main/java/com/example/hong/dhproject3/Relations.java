package com.example.hong.dhproject3;

public class Relations {
    String u_name, u_type;

    public Relations(String user_name, String user_type){
        this.u_name = user_name;
        this.u_type = user_type;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_type() {
        if(this.u_type.equals("s"))
                return "S";
        else
            return "M";
    }

    public void setU_type(String u_type) {
        this.u_type = u_type;
    }
}
