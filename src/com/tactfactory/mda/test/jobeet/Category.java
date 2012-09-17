package com.tactfactory.mda.test.jobeet;

import java.util.ArrayList;

import com.tactfactory.mda.orm.annotation.*;

@Table
@Entity
public class Category {
    @Id
    @Column()				// type="integer"
    @GeneratedValue(strategy="IDENTITY")
    protected int id;

    @Column( unique=true)	// type="string", length=255
    protected String name;
    
    @ManyToMany()
	protected ArrayList<Affiliate> affiliates;
}
