package com.example.k23cnt3_nhd_bt3.Entity;

public class nhdStudent {
    Long id;
    String name;
    int age;
    String gender;
    String address;
    String phone;
    String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public nhdStudent(String email, String phone, String address, String gender, int age, String name, Long id) {
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.age = age;
        this.name = name;
        this.id = id;
    }

    public nhdStudent() {
    }
}
