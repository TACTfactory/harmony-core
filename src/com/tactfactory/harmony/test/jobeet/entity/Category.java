package com.tactfactory.harmony.test.jobeet.entity;

import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToMany;
import com.tactfactory.harmony.annotation.Table;

@Table
@Entity
public class Category {
    @Id
    @Column()
    @GeneratedValue(strategy = "IDENTITY")
    private int id;

    @Column(unique = true)
    private String name;

    @ManyToMany()
    private ArrayList<Affiliate> affiliates;

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
