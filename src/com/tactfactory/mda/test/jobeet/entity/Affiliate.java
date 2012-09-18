package com.tactfactory.mda.test.jobeet.entity;

import java.util.ArrayList;
import java.util.Date;

import com.tactfactory.mda.orm.annotation.*;

@Table
@Entity
public class Affiliate {
	@Id
    @Column()					// type="integer",
    @GeneratedValue(strategy="IDENTITY")
    protected int id;
	
	@Column(nullable=true)		// type="string", length=255 
    protected String url;
	
	@Column()					// type="string", length="255"
    protected String email;
	
	@Column(unique=true)		// type="string", length="255", 
    protected String token;
	
	@Column(name="is_activated")// type="boolean", 
    protected boolean isActivated;
	
	@Column(name="created_at")	// type="datetime",
    protected Date createdAt;
	
	@ManyToMany()
	protected ArrayList<Category> categories;

	public Affiliate() {
		this.createdAt = new Date();
	}
}
