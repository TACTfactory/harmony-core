package com.tactfactory.mda.test.jobeet.entity;

import java.util.Date;
import com.tactfactory.mda.orm.annotation.*;

@Table
@Entity
public class Job {
    @Id
    @Column()					// type="integer",
    @GeneratedValue(strategy="IDENTITY")
    protected int id;

    @ManyToOne() 				// targetEntity="Category"
    @JoinColumn(name="category_id", referencedColumnName="id")
    protected Category category;

    @Column()					// type="string", length=255
    protected String type;

    @Column(nullable=true)		// type="string", length=255
    protected String company;

    @Column()					// type="string", length=255
    protected String logo;

    @Column(nullable=true)		// type="string", length=255 
    protected String url;

    @Column(nullable=true)		// type="string", length=255
    protected String position;

    @Column()					// type="string", length=255
    protected String location;

    @Column(length=4000)		// type="string",
    protected String description;

    @Column(length=4000, name="how_to_apply")	// type="string", 
    protected String howToApply;

    @Column(unique=true)		// type="string", length="255", 
    protected String token;

    @Column(name="is_public")	// type="boolean", 
    protected boolean isPublic;

    @Column(name="is_activated")// type="boolean", 
    protected boolean isActivated;

    @Column()					// type="string", length="255"
    protected String email;

    @Column(name="created_at")	// type="datetime",
    protected Date createdAt;

    @Column(name="updated_at")	// type="datetime", 
    protected Date updatedAt;

    @Column(name="expires_at")	// type="datetime", 
    protected Date expiresAt;

    public Job() {
    	this.createdAt = new Date();
    	this.updatedAt = new Date();
    }
}
