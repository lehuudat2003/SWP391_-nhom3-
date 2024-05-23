/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author thach
 */
public class Admin {

    private int id;
    private String name;
    private String email;
    private String password;
    private String address;
    private Boolean gender;
    private String phone;
    private Roles roles;
    private String created_by;
    private String created_on;   
    private String modifile_by;   
    private String modifile_on;
    private String img;
    

    public Admin() {
    }

    public Admin(int id, String name, String email, String password, String address, Boolean gender, String phone, Roles roles, String created_by, String created_on, String modifile_by, String modifile_on) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.phone = phone;
        this.roles = roles;
        this.created_by = created_by;
        this.created_on = created_on;
        this.modifile_by = modifile_by;
        this.modifile_on = modifile_on;
    }





    public Admin(int id, String name, String email, String password, String address, Boolean gender, String phone, Roles roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.phone = phone;
        this.roles = roles;
    }



    public Admin(String name, String email, String password, String address, String phone, Roles roles) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;

        this.phone = phone;
        this.roles = roles;
    }
    

    public Admin(int id, String name, String email, String password, String address, Boolean gender, String phone, Roles roles, String created_by, String created_on, String modifile_by, String modifile_on, String img) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.phone = phone;
        this.roles = roles;
        this.created_by = created_by;
        this.created_on = created_on;
        this.modifile_by = modifile_by;
        this.modifile_on = modifile_on;
        this.img = img;
    }
    public Admin( String name, String email, String address, Boolean gender, String phone, Roles roles, String img) {

        this.name = name;
        this.email = email;

        this.address = address;
        this.gender = gender;
        this.phone = phone;
        this.roles = roles;

        this.img = img;
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

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getModifile_by() {
        return modifile_by;
    }

    public void setModifile_by(String modifile_by) {
        this.modifile_by = modifile_by;
    }

    public String getModifile_on() {
        return modifile_on;
    }

    public void setModifile_on(String modifile_on) {
        this.modifile_on = modifile_on;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }







}
