package com.tactfactory.mda.test.demact;

import java.util.Date;

import com.tactfactory.mda.orm.annotation.*;
import com.tactfactory.mda.orm.annotation.Column.Type;

@Table
@Entity
public class User {
	@Id
    @Column()					// type="integer",
    @GeneratedValue(strategy="IDENTITY")
    protected int id;

	@Column(unique=true)		// type="string", length=255
    protected String login;
	
	@Column(type=Type.PASSWORD)	// type="string", length=255
    protected String password;
	
	@Column()					// type="string", length=255
    protected String firstname;
	
	@Column()					// type="string", length=255
    protected String lastname;
	
	@Column(name="created_at")	// type="datetime",
    protected Date createdAt;
}
