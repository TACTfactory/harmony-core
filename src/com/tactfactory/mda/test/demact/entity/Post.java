package com.tactfactory.mda.test.demact.entity;

import java.util.ArrayList;
import java.util.Date;

import com.tactfactory.mda.orm.annotation.*;

@Table
@Entity
public class Post {
	@Id
    @Column()					// type="integer",
    @GeneratedValue(strategy="IDENTITY")
    protected int id;
	
	@Column(unique=true)	// type="string", length=255
    protected String title;
	
	@Column(length=40000)	// type="string"
    protected String content;
	
	@ManyToOne
	protected User owner;
	
	@OneToMany
	protected ArrayList<Comment> comments;
	
	@Column(name="created_at")	// type="datetime",
    protected Date createdAt;

    @Column(name="updated_at")	// type="datetime", 
    protected Date updatedAt;

    @Column(name="expires_at")	// type="datetime", 
    protected Date expiresAt;

    public Post() {
    	this.createdAt = new Date();
    	this.updatedAt = new Date();
    }
}
