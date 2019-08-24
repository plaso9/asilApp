package it.uniba.di.sms.asilapp.models;

public class Parameter {
    public String name;

    //Empty Constructor
    public Parameter(){}

    //Constructor with param
    public Parameter(String name) {
        this.name = name;
    }

    //Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
