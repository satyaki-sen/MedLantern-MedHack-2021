package com.satyaki.medtech;

public class UserEmergency {

    String email,condition,address,name,number;

    public UserEmergency(){


    }


    public UserEmergency(String email, String condition, String address,String name,String number) {
        this.email = email;
        this.condition = condition;
        this.address = address;
        this.name=name;
        this.number=number;
    }

    public String getEmail() {
        return email;
    }

    public String getCondition() {
        return condition;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
