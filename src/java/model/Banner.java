/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author thach
 */
public class Banner {

    private int id;
    private String img;
    private String title;
    private String content;
    
    private int created_id;
    private String created_on;
    private int modifile_id;
    private String modifile_on;

    public Banner() {
    }

    public Banner(int id, String img, String title, String content, int created_id, String created_on, int modifile_id, String modifile_on) {
        this.id = id;
        this.img = img;
        this.title = title;
        this.content = content;
       
        this.created_id = created_id;
        this.created_on = created_on;
        this.modifile_id = modifile_id;
        this.modifile_on = modifile_on;
    }
    
        public Banner( String img, String title, String content, int created_id, String created_on, int modifile_id, String modifile_on) {
        
        this.img = img;
        this.title = title;
        this.content = content;
       
        this.created_id = created_id;
        this.created_on = created_on;
        this.modifile_id = modifile_id;
        this.modifile_on = modifile_on;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public int getCreated_id() {
        return created_id;
    }

    public void setCreated_id(int created_id) {
        this.created_id = created_id;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public int getModifile_id() {
        return modifile_id;
    }

    public void setModifile_id(int modifile_id) {
        this.modifile_id = modifile_id;
    }

    public String getModifile_on() {
        return modifile_on;
    }

    public void setModifile_on(String modifile_on) {
        this.modifile_on = modifile_on;
    }
    

}
