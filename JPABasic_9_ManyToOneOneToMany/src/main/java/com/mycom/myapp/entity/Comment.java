package com.mycom.myapp.entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
// Owner Entity
@Entity
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String content;
    // #1 ~ #8, #10
    // #A, #B, #C
//  @ManyToOne
//  @JoinColumn(name="post_id") // 생성되는 Comment table 에 post_id 컬럼이 생성 (FK)
//  private Post post;
    
    // #9
//  @ManyToOne(cascade = CascadeType.PERSIST)
//  @JoinColumn(name="post_id") // 생성되는 Comment table 에 post_id 컬럼이 생성 (FK)
//  private Post post;  
    
    // #D
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id") // 생성되는 Comment table 에 post_id 컬럼이 생성 (FK)
    private Post post;      
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }
    @Override
    public String toString() {
        return "Comment [id=" + id + ", content=" + content + "]";
    }
    
}
