package it.uniba.di.sms.asilapp.models;

public class User {
    public String name;
    public String surname;
    public String date_of_birth;
    public String birth_place;
    public String cell;
    public String gender;

//empty constructor
    public User(){

    }
//constructor
    public User(String name, String surname, String date_of_birth, String birth_place, String cell, String gender) {
        this.name = name;
        this.surname = surname;
        this.date_of_birth = date_of_birth;
        this.birth_place = birth_place;
        this.cell = cell;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getBirth_place() {
        return birth_place;
    }

    public void setBirth_place(String birth_place) {
        this.birth_place = birth_place;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }
}
