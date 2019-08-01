package it.uniba.di.sms.asilapp.models;

public class Acceptance {
    public String name;
    public String address;
    public String regulation;

    //empty constructor
    public Acceptance(){

    }

    //constructor
    public Acceptance(String name, String address, String regulation){
        this.name = name;
        this.address = address;
        this.regulation = regulation;
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
