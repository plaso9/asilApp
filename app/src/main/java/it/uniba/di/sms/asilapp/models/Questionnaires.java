package it.uniba.di.sms.asilapp.models;

public class Questionnaires {
    public String name;
    public String uri;

    //Empty construnctor
    public Questionnaires() {
    }

    //Constructor with params
    public Questionnaires(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
