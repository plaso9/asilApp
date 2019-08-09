package it.uniba.di.sms.asilapp.models;

import java.util.ArrayList;

public class Acceptance {
    public String name;
    public String address;
    public String regulation;
    public ArrayList<String> listOfServices;

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
    public Acceptance(String name, String address, ArrayList<String> listOfServices){
        this.name = name;
        this.address = address;
        this.listOfServices = listOfServices;
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
}
