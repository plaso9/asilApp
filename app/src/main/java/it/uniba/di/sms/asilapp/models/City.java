package it.uniba.di.sms.asilapp.models;

public class City {
    public String name;
    public String description;
    public long id;

    //empty constructor
    public City(){

    }
    //constructor
    public City(String name, String description, long id) {
        this.name = name;
        this.description = description;
        this.id = id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
