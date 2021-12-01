package com.example.sqlapplication.model;

public class Customer {
    //atributos
    private int idcustomer;
    private String name;
    private String gender;
    private String phone;
    private String address;
    private String email;

    //constructor con parámetros
    public Customer(int idcustomer, String name, String gender, String phone, String address, String email) {
        this.idcustomer = idcustomer;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    //constructor vacío
    public Customer() {
    }

    //getter and setter

    public int getIdcustomer() {
        return idcustomer;
    }

    public void setIdcustomer(int idcustomer) {
        this.idcustomer = idcustomer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
