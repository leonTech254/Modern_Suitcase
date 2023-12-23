package com.leonteqsecurity.suitcasenew.Models;

import android.widget.ImageView;

public class CardItemVacations {
    private String VacationLocation;
    private String imageBackground;
    private  int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }


    public CardItemVacations(String vacationLocation, String imageBackground, int id) {
        VacationLocation = vacationLocation;
        this.imageBackground = imageBackground;
        Id = id;
    }

    public CardItemVacations(String vacationLocation, String imageBackground) {
        VacationLocation = vacationLocation;
        this.imageBackground = imageBackground;
    }

    public String getVacationLocation() {
        return VacationLocation;
    }

    public void setVacationLocation(String vacationLocation) {
        VacationLocation = vacationLocation;
    }

    public String getImageBackground() {
        return imageBackground;
    }
    public void setImageBackground(String imageBackground) {
        this.imageBackground = imageBackground;
    }
}
