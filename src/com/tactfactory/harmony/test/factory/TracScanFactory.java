package com.tactfactory.harmony.test.factory;

import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;

/**
 * This class is used to generate the test metadata for the project TracScan.
 */
public class TracScanFactory extends ProjectMetadataFactory {

	/**
	 * Generate the test metadata.
	 * 
	 * @return The test metadata
	 */
	public static ApplicationMetadata generateTestMetadata() {
		ApplicationMetadata tracscan = new ApplicationMetadata();
		tracscan.setName("tracsan");
		tracscan.setProjectNameSpace("com/tactfactory/tracscan");
		
		EntityMetadata itemProd = new EntityMetadata();
		EntityMetadata logProd = new EntityMetadata();
		EntityMetadata orderProd = new EntityMetadata();
		EntityMetadata user = new EntityMetadata();
		EntityMetadata zone = new EntityMetadata();
		
		itemProd.setName("ItemProd");
		logProd.setName("LogProd");
		orderProd.setName("OrderProd");
		user.setName("User");
		zone.setName("Zone");
		
		tracscan.getEntities().put(itemProd.getName(), itemProd);
		tracscan.getEntities().put(logProd.getName(), logProd);
		tracscan.getEntities().put(orderProd.getName(), orderProd);
		tracscan.getEntities().put(user.getName(), user);
		tracscan.getEntities().put(zone.getName(), zone);
		
		
		// Imports
		itemProd.getImports().add(CLASS_SERIALIZABLE);
		itemProd.getImports().add(CLASS_PARCELABLE);
		itemProd.getImports().add(CLASS_PARCEL);
		itemProd.getImports().add(CLASS_DATETIME);
		itemProd.getImports().add(CLASS_COLUMN);
		itemProd.getImports().add(CLASS_ENTITY);
		itemProd.getImports().add(CLASS_ID);
		itemProd.getImports().add(CLASS_MANY_TO_ONE);
		itemProd.getImports().add(CLASS_TYPE);
		itemProd.getImports().add(CLASS_ARRAYLIST);
		itemProd.getImports().add(CLASS_LIST);

		logProd.getImports().add(CLASS_SERIALIZABLE);
		logProd.getImports().add(CLASS_PARCELABLE);
		logProd.getImports().add(CLASS_PARCEL);
		logProd.getImports().add(CLASS_DATETIME);
		logProd.getImports().add(CLASS_COLUMN);
		logProd.getImports().add(CLASS_ENTITY);
		logProd.getImports().add(CLASS_ID);
		logProd.getImports().add(CLASS_MANY_TO_ONE);
		logProd.getImports().add(CLASS_TYPE);
		logProd.getImports().add(CLASS_ARRAYLIST);
		logProd.getImports().add(CLASS_LIST);

		orderProd.getImports().add(CLASS_SERIALIZABLE);
		orderProd.getImports().add(CLASS_PARCELABLE);
		orderProd.getImports().add(CLASS_PARCEL);
		orderProd.getImports().add(CLASS_COLUMN);
		orderProd.getImports().add(CLASS_ENTITY);
		orderProd.getImports().add(CLASS_ID);
		orderProd.getImports().add(CLASS_ONE_TO_MANY);
		orderProd.getImports().add(CLASS_TYPE);
		orderProd.getImports().add(CLASS_ARRAYLIST);
		orderProd.getImports().add(CLASS_LIST);

		user.getImports().add(CLASS_SERIALIZABLE);
		user.getImports().add(CLASS_PARCELABLE);
		user.getImports().add(CLASS_PARCEL);
		user.getImports().add(CLASS_COLUMN);
		user.getImports().add(CLASS_ENTITY);
		user.getImports().add(CLASS_ID);
		user.getImports().add(CLASS_TYPE);
		user.getImports().add(CLASS_ARRAYLIST);
		user.getImports().add(CLASS_LIST);

		zone.getImports().add(CLASS_SERIALIZABLE);
		zone.getImports().add(CLASS_PARCELABLE);
		zone.getImports().add(CLASS_PARCEL);
		zone.getImports().add(CLASS_COLUMN);
		zone.getImports().add(CLASS_ENTITY);
		zone.getImports().add(CLASS_ID);
		zone.getImports().add(CLASS_TYPE);
		zone.getImports().add(CLASS_ARRAYLIST);
		zone.getImports().add(CLASS_LIST);
		
		// Interfaces
		user.getImplementTypes().add(CLASS_SERIALIZABLE);
		user.getImplementTypes().add(CLASS_PARCELABLE);

		zone.getImplementTypes().add(CLASS_SERIALIZABLE);
		zone.getImplementTypes().add(CLASS_PARCELABLE);

		logProd.getImplementTypes().add(CLASS_SERIALIZABLE);
		logProd.getImplementTypes().add(CLASS_PARCELABLE);

		orderProd.getImplementTypes().add(CLASS_SERIALIZABLE);
		orderProd.getImplementTypes().add(CLASS_PARCELABLE);

		itemProd.getImplementTypes().add(CLASS_SERIALIZABLE);
		itemProd.getImplementTypes().add(CLASS_PARCELABLE);
		
		// Ids
		ProjectMetadataFactory.generateIdField(user, "id");
		ProjectMetadataFactory.generateIdField(zone, "id");
		ProjectMetadataFactory.generateIdField(logProd, "id");
		ProjectMetadataFactory.generateIdField(orderProd, "id");
		ProjectMetadataFactory.generateIdField(itemProd, "id");
		
		// Fields
		ProjectMetadataFactory.generateField(
				user, "type", false, false);
		ProjectMetadataFactory.generateField(
				user, "login", false, true);
		ProjectMetadataFactory.generateField(
				user, "passwd", false, false);
		
		ProjectMetadataFactory.generateField(
				zone, "name", false, false);
		ProjectMetadataFactory.generateField(
				zone, "quantity", false, true);

		ProjectMetadataFactory.generateField(
				itemProd, "name", false, false);
		ProjectMetadataFactory.generateField(
				itemProd, "state", false, false);
		ProjectMetadataFactory.generateField(
				itemProd, "updateDate", false, false);
		ProjectMetadataFactory.generateField(
				itemProd, "orderCustomer", false, false);
		ProjectMetadataFactory.generateField(
				itemProd, "currentZone", false, false);
		ProjectMetadataFactory.generateField(
				itemProd,
				"OrderProditemsInternal",
				true,
				false,
				"OrderProd_items_internal");

		ProjectMetadataFactory.generateField(
				logProd, "createDate", false, false);
		ProjectMetadataFactory.generateField(
				logProd, "stateAction", false, false);
		ProjectMetadataFactory.generateField(
				logProd, "zoneLogged", false, false);
		ProjectMetadataFactory.generateField(
				logProd, "userLogged", false, false);
		ProjectMetadataFactory.generateField(
				logProd, "itemLogged", false, false);

		ProjectMetadataFactory.generateField(
				orderProd, "customer", false, false);
		ProjectMetadataFactory.generateField(
				orderProd, "productType", false, false);
		ProjectMetadataFactory.generateField(
				orderProd, "materialType", false, false);
		ProjectMetadataFactory.generateField(
				orderProd, "quantity", false, false);
		ProjectMetadataFactory.generateField(
				orderProd, "items", true, false);
		
		return tracscan;
	}
}
