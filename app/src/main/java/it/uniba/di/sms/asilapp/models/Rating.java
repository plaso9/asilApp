package it.uniba.di.sms.asilapp.models;

public class Rating {
    public String user;
    public Float avgRating;
    public String comment;

    //Empty constructor
    public Rating() {}

    //Constructor with params
    public Rating(String user, Float avgRating, String comment) {
        this.user = user;
        this.avgRating = avgRating;
        this.comment = comment;
    }

    //Getter and Setter methods
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
