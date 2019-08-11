package it.uniba.di.sms.asilapp.models;

public class Temperature {
    public String value;
    public String _user;
    public String _parameter;
    public String data_measurement;

    public Temperature(String value, String _user, String _parameter, String data_measurement) {
        this.value = value;
        this._user = _user;
        this._parameter = _parameter;
        this.data_measurement = data_measurement;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String get_user() {
        return _user;
    }

    public void set_user(String _user) {
        this._user = _user;
    }

    public String get_parameter() {
        return _parameter;
    }

    public void set_parameter(String _parameter) {
        this._parameter = _parameter;
    }

    public String getData_measurement() {
        return data_measurement;
    }

    public void setData_measurement(String data_measurement) {
        this.data_measurement = data_measurement;
    }
}
