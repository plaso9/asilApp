package it.uniba.di.sms.asilapp.models;

public class Pathology {
    public String name;
    public String description;
    public String nutritional;
    public String lifestyle;
    public String medicine;

    //Empty constructor
    public Pathology() {
    }

    //Constructor with params
    public Pathology(String name, String description, String nutritional, String lifestyle, String medicine) {
        this.name = name;
        this.description = description;
        this.nutritional = nutritional;
        this.lifestyle = lifestyle;
        this.medicine = medicine;
    }

    //Getter and Setter methods
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

    public String getNutritional() {
        return nutritional;
    }

    public void setNutritional(String nutritional) {
        this.nutritional = nutritional;
    }

    public String getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(String lifestyle) {
        this.lifestyle = lifestyle;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }
}
