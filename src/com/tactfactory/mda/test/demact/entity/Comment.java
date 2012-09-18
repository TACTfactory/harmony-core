package com.tactfactory.mda.test.demact.entity;

import java.util.Date;

import com.tactfactory.mda.orm.annotation.*;

@Table
@Entity
public class Comment {
	@Id
    @Column()					// type="integer",
    @GeneratedValue(strategy="IDENTITY")
    protected int id;

	@Column(length=2000)	// type="string"
    protected String content;
	
	@ManyToOne
	protected User owner;
	
	@ManyToOne
	protected Post post;
	
	@Column(name="created_at")	// type="datetime",
    protected Date createdAt;
}
