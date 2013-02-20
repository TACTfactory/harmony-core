package com.tactfactory.mda.test.jobeet.entity;

import java.util.ArrayList;

import com.tactfactory.mda.annotation.Column;
import com.tactfactory.mda.annotation.Entity;
import com.tactfactory.mda.annotation.GeneratedValue;
import com.tactfactory.mda.annotation.Id;
import com.tactfactory.mda.annotation.ManyToMany;
import com.tactfactory.mda.annotation.Table;

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
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(final int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the affiliates
	 */
	public final ArrayList<Affiliate> getAffiliates() {
		return this.affiliates;
	}

	/**
	 * @param affiliates the affiliates to set
	 */
	public final void setAffiliates(final ArrayList<Affiliate> affiliates) {
		this.affiliates = affiliates;
	}
}
