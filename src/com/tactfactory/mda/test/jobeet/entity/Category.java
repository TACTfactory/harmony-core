package com.tactfactory.mda.test.jobeet.entity;

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

	/**
	 * @return the id
	 */
	public final int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the affiliates
	 */
	public final ArrayList<Affiliate> getAffiliates() {
		return affiliates;
	}

	/**
	 * @param affiliates the affiliates to set
	 */
	public final void setAffiliates(ArrayList<Affiliate> affiliates) {
		this.affiliates = affiliates;
	}
}
