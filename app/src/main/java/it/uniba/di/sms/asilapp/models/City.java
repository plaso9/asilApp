package it.uniba.di.sms.asilapp.models;

public class City {
    public String name;
    public String description;

    //empty constructor
    public City(){

    }
    //constructor
    public City(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
