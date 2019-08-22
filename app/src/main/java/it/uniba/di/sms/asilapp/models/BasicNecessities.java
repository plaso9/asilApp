package it.uniba.di.sms.asilapp.models;

public class BasicNecessities {
    public String name;
    public String cityName;

    //empty constructor
    public BasicNecessities(){

    }

    //constructor
    public BasicNecessities(String name, String address, String regulation){
        this.name = name;
        this.cityName = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
