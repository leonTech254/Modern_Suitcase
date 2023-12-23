package com.leonteqsecurity.suitcasenew.Models;

import android.widget.ImageView;

public class CardItemVacations {
    private String VacationLocation;
    private String imageBackground;

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
