package com.satyaki.medtech;

class MedicineClass {

    String day,month,morning,afternoon,evening,night;

    MedicineClass(){

    }


    public MedicineClass(String day, String month, String morning, String afternoon, String evening, String night) {
        this.day = day;
        this.month = month;
        this.morning = morning;
        this.afternoon = afternoon;
        this.evening = evening;
        this.night = night;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getMorning() {
        return morning;
    }

    public String getAfternoon() {
        return afternoon;
    }

    public String getEvening() {
        return evening;
    }

    public String getNight() {
        return night;
    }
}
