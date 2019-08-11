package it.uniba.di.sms.asilapp.models;

public class Rating {
    public String user;
    public Float avgRating;

    //Empty construnctor
    public Rating() {}

    //Constructor with params
    public Rating(String user, Float avgRating) {
        this.user = user;
        this.avgRating = avgRating;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Float avgRating) {
        this.avgRating = avgRating;
    }
}
