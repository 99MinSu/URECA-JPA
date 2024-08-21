package com.mycom.myapp.entity;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
// Non-Owner Entity
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String title;
    
    private String content;
    // #1 ~ #9
    // #A, #B, #D
    @OneToMany(mappedBy="post")  // bi-directional relationship 규정 ( Comment Entity 의 자신 Post 필드 명 )
    private List<Comment> comments;
    // #10
//  @OneToMany(cascade = CascadeType.PERSIST)
//  private List<Comment> comments;
    
    // #C
//  @OneToMany(mappedBy="post", fetch = FetchType.EAGER)  // bi-directional relationship 규정 ( Comment Entity 의 자신 Post 필드 명 )
//  private List<Comment> comments;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    
    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    @Override
    public String toString() {
        return "Post [id=" + id + ", title=" + title + ", content=" + content + "]";
    }
}