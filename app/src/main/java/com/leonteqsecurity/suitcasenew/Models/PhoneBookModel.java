package com.leonteqsecurity.suitcasenew.Models;

public class PhoneBookModel {
    private  String phone_name;
    private  String phon_number;

    public PhoneBookModel(String phone_name, String phon_number) {
        this.phone_name = phone_name;
        this.phon_number = phon_number;
    }

    public String getPhone_name() {
        return phone_name;
    }

    public void setPhone_name(String phone_name) {
        this.phone_name = phone_name;
    }

    public String getPhon_number() {
        return phon_number;
    }

    public void setPhon_number(String phon_number) {
        this.phon_number = phon_number;
    }
}
