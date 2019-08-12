package it.uniba.di.sms.asilapp.models;

public class Message {
    public String user_id;
    public String message;
    public String date;

    //empty constructor
    public Message() {
    }

    //constructor with params
    public Message(String user_id, String message, String date) {
        this.user_id = user_id;
        this.message = message;
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

//    public String getUser_name() {
//        return user_name;
//    }
//
//    public void setUser_name(String user_name) {
//        this.user_name = user_name;
//    }

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
}
