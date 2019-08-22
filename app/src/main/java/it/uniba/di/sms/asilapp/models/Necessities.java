package it.uniba.di.sms.asilapp.models;

public class Necessities {
    public long city;
    public String mall;
    public String pharmacy;

    //Empty constructor
    public Necessities(){}

    //Constructor with params
    public Necessities(long city, String mall, String pharmacy) {
        this.city = city;
        this.mall = mall;
        this.pharmacy = pharmacy;
    }

    public long getCity() {
        return city;
    }

    public void setCity(long city) {
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
