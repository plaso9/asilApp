package it.uniba.di.sms.asilapp.models;

public class Message {
    public String user_id;
    public String message;
    public String date;
    public String name_sender;
    public String surname_sender;

    //empty constructor
    public Message() {
    }

    //constructor with params
    public Message(String name_sender,String surname_sender, String message, String date, String user_id) {
        this.name_sender = name_sender;
        this.surname_sender = surname_sender;
        this.message = message;
        this.date = date;
        this.user_id = user_id;
    }

    //Getter and Setter methods
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName_sender() {
        return name_sender;
    }

    public void setName_sender(String name_sender) {
        this.name_sender = name_sender;
    }

    public String getSurname_sender() {
        return surname_sender;
    }

    public void setSurname_sender(String surname_sender) {
        this.surname_sender = surname_sender;
    }
}
