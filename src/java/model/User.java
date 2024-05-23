/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String address;
    private Boolean gender;
    private String phone;
    private String q1_id;
    private String q1_ans;
    private String q2_id;
    private String q2_ans;
    private Roles roles;
    private String img;

    public User() {
    }

    public User(int id, String name, String email, String password, String address, Boolean gender, String phone, Roles roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.phone = phone;
        this.roles = roles;
    }

    public User(int id, String name, String email, String password, String address, Boolean gender, String phone, String q1_id, String q1_ans, String q2_id, String q2_ans, Roles roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.phone = phone;
        this.q1_id = q1_id;
        this.q1_ans = q1_ans;
        this.q2_id = q2_id;
        this.q2_ans = q2_ans;
        this.roles = roles;
    }

    public User(String name, String email, String password, String address, String phone, Boolean gender, String q1_id, String q1_ans, String q2_id, String q2_ans, Roles roles) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.q1_id = q1_id;
        this.q1_ans = q1_ans;
        this.q2_id = q2_id;
        this.q2_ans = q2_ans;
        this.roles = roles;
    }

    public User(String name, String email, String password, String address, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    public User(String name, String email, String password, String address, String phone, Roles roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.roles = roles;
    }

    public User(String name, String email, String password, String address, Boolean gender, Roles roles, String img) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.roles = roles;
        this.img = img;
    }

    public User(int id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }

    public User(int id, String name, String email, String password, String address, Boolean gender, String phone, Roles roles, String q1_id, String q1_ans, String q2_id, String q2_ans, String img) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.phone = phone;
        this.roles = roles;
        this.q1_id = q1_id;
        this.q1_ans = q1_ans;
        this.q2_id = q2_id;
        this.q2_ans = q2_ans;
        this.img = img;
    }

    public User(String name, String email, String address, Boolean gender, String phone, Roles roles, String img) {
        this.name = name;
        this.email = email;

        this.address = address;
        this.gender = gender;
        this.phone = phone;
        this.roles = roles;

        this.img = img;
    }

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQ1_id() {
        return q1_id;
    }

    public void setQ1_id(String q1_id) {
        this.q1_id = q1_id;
    }

    public String getQ1_ans() {
        return q1_ans;
    }

    public void setQ1_ans(String q1_ans) {
        this.q1_ans = q1_ans;
    }

    public String getQ2_id() {
        return q2_id;
    }

    public void setQ2_id(String q2_id) {
        this.q2_id = q2_id;
    }

    public String getQ2_ans() {
        return q2_ans;
    }

    public void setQ2_ans(String q2_ans) {
        this.q2_ans = q2_ans;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
