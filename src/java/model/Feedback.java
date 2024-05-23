/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author thach
 */
public class Feedback {

    private int id;
    private String comment;

    private int product_id;
    private User user_id;

    public Feedback() {
    }

//    public Feedback(int id, String comment, int product_id, int user_id) {
//        this.id = id;
//        this.comment = comment;
//        this.product_id = product_id;
//        this.user_id = user_id;
//    }

    public Feedback(int id, String comment, int product_id, User user_id) {
        this.id = id;
        this.comment = comment;
        this.product_id = product_id;
        this.user_id = user_id;
    }


    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

//    public int getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(int user_id) {
//        this.user_id = user_id;
//    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }
    
    






}
