package com.tactfactory.harmony.test.management.entity;

import org.joda.time.DateTime;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.InheritanceType;
import com.tactfactory.harmony.annotation.InheritanceType.InheritanceMode;

@Entity
@InheritanceType(InheritanceMode.JOINED)
public class Furniture {

    @Id
    private int id;
    
    @Column
    private int price;
    
    @Column
    private DateTime purchaseDate;
}
