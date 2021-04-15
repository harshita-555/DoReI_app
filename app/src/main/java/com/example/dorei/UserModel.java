package com.example.dorei;

public class UserModel {

    private int id;
    private String name;
    private String password;
    private String email;
    private String house_number, street_number, street_name, city, state, postal_code;

    public UserModel(int id, String name, String password, String email,String house_number,String street_number,String street_name,String city,String state,String postal_code)
    {
        this.name = name;
        this.id = id;
        this.password = password;
        this.email =email;
        this.house_number = house_number;
        this.street_name = street_name;
        this.street_number = street_number;
        this.city = city;
        this.state = state;
        this.postal_code = postal_code;
    }

    public UserModel() {
    }

    public String getHouse_number() {
        return house_number;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }

    public String getStreet_number() {
        return street_number;
    }

    public void setStreet_number(String street_number) {
        this.street_number = street_number;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
