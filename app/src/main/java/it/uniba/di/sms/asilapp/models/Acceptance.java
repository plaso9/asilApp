package it.uniba.di.sms.asilapp.models;

import java.util.ArrayList;

public class Acceptance {
    public String id;
    public String name;
    public String address;
    public String regulation;
    public ArrayList<String> listOfServices;
    public long city;

    //empty constructor
    public Acceptance(){

    }

    //constructor
    public Acceptance(String name, String address, String regulation){
        this.name = name;
        this.address = address;
        this.regulation = regulation;
    }
    //constructor
    public Acceptance(String name, String address, ArrayList<String> listOfServices, String id, long city){
        this.name = name;
        this.address = address;
        this.listOfServices = listOfServices;
        this.id = id;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegulation() {
        return regulation;
    }

    public void setRegulation(String regulation) {
        this.regulation = regulation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCity() {
        return city;
    }

    public void setCity(long city) {
        this.city = city;
    }
}
