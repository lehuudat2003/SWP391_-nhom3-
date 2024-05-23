/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

public class Blog {

    private int id;
    private String banner;
    private String blogTitle;
    private String blogCategory;
    private String introduction;
    private String body;
    private String conclusion;
    private String img1;
    private Date createDate;
    private int viewCount;
    private boolean status;
    private String author;  // Added author field
    private BlogCategory blogCategoryObject;

    // Constructors, getters, and setters
    public Blog() {
    }

    // Constructors
    public Blog(int id, String banner, String blogTitle, String blogCategory, String introduction, String body, String conclusion,
            String img1, Date createDate, int viewCount, boolean status, String author, BlogCategory blogCategoryObject) {
        this.id = id;
        this.banner = banner;
        this.blogTitle = blogTitle;
        this.blogCategory = blogCategory;
        this.introduction = introduction;
        this.body = body;
        this.conclusion = conclusion;
        this.img1 = img1;
        this.createDate = createDate;
        this.viewCount = viewCount;
        this.status = status;
        this.author = author;
        this.blogCategoryObject = blogCategoryObject;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogCategory() {
        return blogCategory;
    }

    public void setBlogCategory(String blogCategory) {
        this.blogCategory = blogCategory;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BlogCategory getBlogCategoryObject() {
        return blogCategoryObject;
    }

    public void setBlogCategoryObject(BlogCategory blogCategoryObject) {
        this.blogCategoryObject = blogCategoryObject;
    }

    @Override
    public String toString() {
        return "Blog{" + "id=" + id + ", banner=" + banner + ", blogTitle=" + blogTitle + ", blogCategory=" + blogCategory
                + ", introduction=" + introduction + ", body=" + body + ", conclusion=" + conclusion + ", img1=" + img1 + ", createDate="
                + createDate + ", viewCount=" + viewCount + ", status=" + status + ", author=" + author + ", blogCategoryObject=" + blogCategoryObject + '}';
    }
}
