package it.uniba.di.sms.asilapp.models;

public class Necessities {
    public String city;
    public String mall;
    public String pharmacy;

    //Empty constructor
    public Necessities(){}

    //Constructor with params
    public Necessities(String city, String mall, String pharmacy) {
        this.city = city;
        this.mall = mall;
        this.pharmacy = pharmacy;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMall() {
        return mall;
    }

    public void setMall(String mall) {
        this.mall = mall;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }
}
