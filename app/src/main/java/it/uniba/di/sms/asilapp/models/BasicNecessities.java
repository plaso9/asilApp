package it.uniba.di.sms.asilapp.models;

public class BasicNecessities {
    public String name;

    //empty constructor
    public BasicNecessities(){

    }

    //constructor
    public BasicNecessities(String name, String address, String regulation){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
