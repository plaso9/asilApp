package it.uniba.di.sms.asilapp.models;

public class Parameter {
    public String name;

    //Empty Constructor
    public Parameter(){}

    //Constructor with param
    public Parameter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
