package com.tactfactory.harmony.test;

import java.util.HashMap;
import java.util.Map;

import com.tactfactory.harmony.annotation.InheritanceType.InheritanceMode;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.meta.InheritanceMetadata;

/**
 * This class is used to generate the test metadata for the project Demact.
 */
public class DemactFactory extends ProjectMetadataFactory {
	
	/**
	 * Generate the test metadata.
	 * 
	 * @return The test metadata
	 */
	public static ApplicationMetadata generateTestMetadata() {
		ApplicationMetadata demact = new ApplicationMetadata();
		demact.setName("demact");
		demact.setProjectNameSpace("com/tactfactory/harmony/test/demact");
		
		// Entities
		EntityMetadata user = new EntityMetadata();
		user.setName("User");
		
		EntityMetadata userToUser = new EntityMetadata();
		userToUser.setName("UsertoUser");
		
		EntityMetadata client = new EntityMetadata();
		client.setName("Client");

		EntityMetadata category = new EntityMetadata();
		category.setName("Category");

		EntityMetadata categoryToComment = new EntityMetadata();
		categoryToComment.setName("CategoryToComment");

		EntityMetadata comment = new EntityMetadata();
		comment.setName("Comment");

		EntityMetadata hiddenEntity = new EntityMetadata();
		hiddenEntity.setName("HiddenEntity");

		EntityMetadata post = new EntityMetadata();
		post.setName("Post");

		EntityMetadata simpleEntity = new EntityMetadata();
		simpleEntity.setName("SimpleEntity");

		EntityMetadata userGroup = new EntityMetadata();
		userGroup.setName("UserGroup");

		EntityMetadata viewComponent = new EntityMetadata();
		viewComponent.setName("ViewComponent");

		EntityMetadata emptyEntity = new EntityMetadata();
		emptyEntity.setName("EmptyEntity");

		EntityMetadata postToCategory = new EntityMetadata();
		postToCategory.setName("PosttoCategory");
		
		demact.getEntities().put(user.getName(), user);
		demact.getEntities().put(userToUser.getName(), userToUser);
		demact.getEntities().put(client.getName(), client);
		demact.getEntities().put(comment.getName(), comment);
		demact.getEntities().put(category.getName(), category);
		demact.getEntities().put(categoryToComment.getName(), categoryToComment);
		demact.getEntities().put(post.getName(), post);
		demact.getEntities().put(emptyEntity.getName(), emptyEntity);
		demact.getEntities().put(simpleEntity.getName(), simpleEntity);
		demact.getEntities().put(userGroup.getName(), userGroup);
		demact.getEntities().put(viewComponent.getName(), viewComponent);
		demact.getEntities().put(hiddenEntity.getName(), hiddenEntity);
		demact.getEntities().put(postToCategory.getName(), postToCategory);
		

		// Set Implements
		user.getImplementTypes().add(CLASS_SERIALIZABLE);
		user.getImplementTypes().add(CLASS_PARCELABLE);
		user.getImplementTypes().add(CLASS_CLONEABLE);
		comment.getImplementTypes().add(CLASS_SERIALIZABLE);
		comment.getImplementTypes().add(CLASS_PARCELABLE);
		post.getImplementTypes().add(CLASS_SERIALIZABLE);
		post.getImplementTypes().add(CLASS_PARCELABLE);
		client.getImplementTypes().add(CLASS_SERIALIZABLE);
		client.getImplementTypes().add(CLASS_PARCELABLE);
		category.getImplementTypes().add(CLASS_SERIALIZABLE);
		category.getImplementTypes().add(CLASS_PARCELABLE);
		categoryToComment.getImplementTypes().add(CLASS_SERIALIZABLE);
		categoryToComment.getImplementTypes().add(CLASS_PARCELABLE);
		hiddenEntity.getImplementTypes().add(CLASS_SERIALIZABLE);
		hiddenEntity.getImplementTypes().add(CLASS_PARCELABLE);
		simpleEntity.getImplementTypes().add(CLASS_SERIALIZABLE);
		simpleEntity.getImplementTypes().add(CLASS_PARCELABLE);
		userGroup.getImplementTypes().add(CLASS_SERIALIZABLE);
		userGroup.getImplementTypes().add(CLASS_PARCELABLE);
		viewComponent.getImplementTypes().add(CLASS_SERIALIZABLE);
		viewComponent.getImplementTypes().add(CLASS_PARCELABLE);
		emptyEntity.getImplementTypes().add(CLASS_SERIALIZABLE);
		emptyEntity.getImplementTypes().add(CLASS_PARCELABLE);
		
		// Set import
		user.getImports().add(CLASS_SERIALIZABLE);
		user.getImports().add(CLASS_PARCELABLE);
		user.getImports().add(CLASS_PARCEL);
		user.getImports().add(CLASS_DATETIME);
		user.getImports().add(CLASS_ENTITY);
		user.getImports().add(CLASS_TYPE);
		user.getImports().add(CLASS_COLUMN);
		user.getImports().add(CLASS_COLUMN_RESULT);
		user.getImports().add(CLASS_DISCRIMINATOR_COLUMN);
		user.getImports().add(CLASS_GENERATED_VALUE);
		user.getImports().add(CLASS_ID);
		user.getImports().add(CLASS_INHERITANCE_TYPE);
		user.getImports().add(CLASS_MANY_TO_ONE);
		user.getImports().add(CLASS_MANY_TO_MANY);
		user.getImports().add(CLASS_TABLE);
		user.getImports().add(CLASS_INHERITANCE_MODE);
		user.getImports().add(CLASS_ARRAYLIST);

		client.getImports().add(CLASS_SERIALIZABLE);
		client.getImports().add(CLASS_PARCELABLE);
		client.getImports().add(CLASS_PARCEL);
		client.getImports().add(CLASS_COLUMN);
		client.getImports().add(CLASS_ENTITY);

		comment.getImports().add(CLASS_SERIALIZABLE);
		comment.getImports().add(CLASS_PARCELABLE);
		comment.getImports().add(CLASS_PARCEL);
		comment.getImports().add(CLASS_COLUMN);
		comment.getImports().add(CLASS_ENTITY);
		comment.getImports().add(CLASS_TABLE);
		comment.getImports().add(CLASS_DATETIME);
		comment.getImports().add(CLASS_ARRAYLIST);
		comment.getImports().add(CLASS_GENERATED_VALUE);
		comment.getImports().add(CLASS_MANY_TO_ONE);
		comment.getImports().add(CLASS_ONE_TO_MANY);
		comment.getImports().add(CLASS_ID);
		comment.getImports().add(CLASS_TYPE);

		category.getImports().add(CLASS_SERIALIZABLE);
		category.getImports().add(CLASS_PARCELABLE);
		category.getImports().add(CLASS_PARCEL);
		category.getImports().add(CLASS_COLUMN);
		category.getImports().add(CLASS_ENTITY);
		category.getImports().add(CLASS_ID);
		category.getImports().add(CLASS_ARRAYLIST);
		category.getImports().add(CLASS_ONE_TO_MANY);

		categoryToComment.getImports().add(CLASS_SERIALIZABLE);
		categoryToComment.getImports().add(CLASS_PARCELABLE);
		categoryToComment.getImports().add(CLASS_PARCEL);
		categoryToComment.getImports().add(CLASS_COLUMN);
		categoryToComment.getImports().add(CLASS_ENTITY);
		categoryToComment.getImports().add(CLASS_ID);
		categoryToComment.getImports().add(CLASS_MANY_TO_ONE);

		post.getImports().add(CLASS_SERIALIZABLE);
		post.getImports().add(CLASS_PARCELABLE);
		post.getImports().add(CLASS_PARCEL);
		post.getImports().add(CLASS_ARRAYLIST);
		post.getImports().add(CLASS_DATETIME);
		post.getImports().add(CLASS_COLUMN);
		post.getImports().add(CLASS_ENTITY);
		post.getImports().add(CLASS_TABLE);
		post.getImports().add(CLASS_TYPE);
		post.getImports().add(CLASS_ID);
		post.getImports().add(CLASS_MANY_TO_ONE);
		post.getImports().add(CLASS_MANY_TO_MANY);
		post.getImports().add(CLASS_ONE_TO_MANY);
		post.getImports().add(CLASS_GENERATED_VALUE);

		emptyEntity.getImports().add(CLASS_SERIALIZABLE);
		emptyEntity.getImports().add(CLASS_PARCELABLE);
		emptyEntity.getImports().add(CLASS_PARCEL);
		emptyEntity.getImports().add(CLASS_ENTITY);

		simpleEntity.getImports().add(CLASS_SERIALIZABLE);
		simpleEntity.getImports().add(CLASS_PARCELABLE);
		simpleEntity.getImports().add(CLASS_PARCEL);
		simpleEntity.getImports().add(CLASS_ENTITY);
		simpleEntity.getImports().add(CLASS_COLUMN);
		simpleEntity.getImports().add(CLASS_GENERATED_VALUE);
		simpleEntity.getImports().add(CLASS_ID);
		simpleEntity.getImports().add(CLASS_TYPE);

		userGroup.getImports().add(CLASS_SERIALIZABLE);
		userGroup.getImports().add(CLASS_PARCELABLE);
		userGroup.getImports().add(CLASS_PARCEL);
		userGroup.getImports().add(CLASS_ENTITY);
		userGroup.getImports().add(CLASS_COLUMN);
		userGroup.getImports().add(CLASS_ID);

		viewComponent.getImports().add(CLASS_SERIALIZABLE);
		viewComponent.getImports().add(CLASS_PARCELABLE);
		viewComponent.getImports().add(CLASS_PARCEL);
		viewComponent.getImports().add(CLASS_COLUMN);
		viewComponent.getImports().add(CLASS_ENTITY);
		viewComponent.getImports().add(CLASS_ID);
		viewComponent.getImports().add(CLASS_GENERATED_VALUE);
		viewComponent.getImports().add(CLASS_TYPE);
		viewComponent.getImports().add(CLASS_DATETIME);

		hiddenEntity.getImports().add(CLASS_SERIALIZABLE);
		hiddenEntity.getImports().add(CLASS_PARCELABLE);
		hiddenEntity.getImports().add(CLASS_PARCEL);
		hiddenEntity.getImports().add(CLASS_DATETIME);
		hiddenEntity.getImports().add(CLASS_ARRAYLIST);
		hiddenEntity.getImports().add(CLASS_ENTITY);
		hiddenEntity.getImports().add(CLASS_TYPE);
		hiddenEntity.getImports().add(CLASS_COLUMN);
		hiddenEntity.getImports().add(CLASS_GENERATED_VALUE);
		hiddenEntity.getImports().add(CLASS_ID);
		hiddenEntity.getImports().add(CLASS_MANY_TO_ONE);
		hiddenEntity.getImports().add(CLASS_MANY_TO_MANY);
		hiddenEntity.getImports().add(CLASS_ONE_TO_MANY);
		hiddenEntity.getImports().add(CLASS_CRUD);
		hiddenEntity.getImports().add(CLASS_TABLE);


		// Set entities attributes
		hiddenEntity.setHidden(true);
		postToCategory.setInternal(true);
		postToCategory.setHidden(true);
		userToUser.setInternal(true);
		userToUser.setHidden(true);
		
		
		// Set inheritance
		InheritanceMetadata userInheritance = new InheritanceMetadata();
		Map<String, EntityMetadata> userSubclasses =
				new HashMap<String, EntityMetadata>();
		userSubclasses.put(client.getName(), client);
		userInheritance.setSubclasses(userSubclasses);
		userInheritance.setType(InheritanceMode.SINGLE_TAB);
		FieldMetadata discriminator = new FieldMetadata(user);
		discriminator.setColumnName("type");
		discriminator.setColumnDefinition("varchar");
		userInheritance.setDiscriminorColumn(discriminator);
		user.setInheritance(userInheritance);

		InheritanceMetadata clientInheritance = new InheritanceMetadata();
		clientInheritance.setSuperclass(user);
		clientInheritance.setType(InheritanceMode.SINGLE_TAB);
		clientInheritance.setDiscriminorIdentifier("Client");
		client.setInheritance(clientInheritance);
		
		
		// Set entities ids
		ProjectMetadataFactory.generateIdField(user, "id");
		ProjectMetadataFactory.generateIdField(post, "id");
		ProjectMetadataFactory.generateIdField(comment, "id");
		ProjectMetadataFactory.generateIdField(category, "id");
		ProjectMetadataFactory.generateIdField(categoryToComment, "id");
		ProjectMetadataFactory.generateIdField(userGroup, "id");
		ProjectMetadataFactory.generateIdField(viewComponent, "id");
		ProjectMetadataFactory.generateIdField(hiddenEntity, "id");
		ProjectMetadataFactory.generateIdField(simpleEntity, "id");
		
		
		// Set entities fields
		ProjectMetadataFactory.generateField(
				user, "login", false, true);
		ProjectMetadataFactory.generateField(
				user, "password", false, false);
		ProjectMetadataFactory.generateField(
				user, "firstname", true, false);
		ProjectMetadataFactory.generateField(
				user, "lastname", false, false);
		ProjectMetadataFactory.generateField(
				user, "createdAt", false, false, "created_at");
		ProjectMetadataFactory.generateField(
				user, "birthdate", false, false);
		ProjectMetadataFactory.generateField(
				user, "userGroup", false, false);
		ProjectMetadataFactory.generateField(
				user, "title", false, false);
		ProjectMetadataFactory.generateField(
				user, "fullName", false, false, "firstname || ' ' || lastname");
		ProjectMetadataFactory.generateField(
				user, "friends", false, true);
		
		ProjectMetadataFactory.generateField(
				client, "adress", false, false);

		ProjectMetadataFactory.generateField(
				comment, "content", false, false);
		ProjectMetadataFactory.generateField(
				comment, "owner", false, false);
		ProjectMetadataFactory.generateField(
				comment, "post", false, false);
		ProjectMetadataFactory.generateField(
				comment, "createdAt", false, false, "created_at");
		ProjectMetadataFactory.generateField(
				comment, "validate", false, false);
		ProjectMetadataFactory.generateField(
				comment, "categories", false, false);
		
		ProjectMetadataFactory.generateField(
				category, "name", false, false);
		ProjectMetadataFactory.generateField(
				category, "comments", false, false);
		
		ProjectMetadataFactory.generateField(
				categoryToComment, "displayName", false, false);
		ProjectMetadataFactory.generateField(
				categoryToComment, "category", false, false);
		ProjectMetadataFactory.generateField(
				categoryToComment,
				"CommentcategoriesInternal",
				true,
				false,
				"Comment_categories_internal");
		
		ProjectMetadataFactory.generateField(
				post, "title", false, false);
		ProjectMetadataFactory.generateField(
				post, "content", false, false);
		ProjectMetadataFactory.generateField(
				post, "owner", false, false);
		ProjectMetadataFactory.generateField(
				post, "comments", false, false);
		ProjectMetadataFactory.generateField(
				post, "categories", false, false);
		ProjectMetadataFactory.generateField(
				post, "createdAt", false, false, "created_at");
		ProjectMetadataFactory.generateField(
				post, "updatedAt", false, false, "updated_at");
		ProjectMetadataFactory.generateField(
				post, "expiresAt", false, false, "expires_at");
		
		ProjectMetadataFactory.generateField(
				userGroup, "name", false, false);
		ProjectMetadataFactory.generateField(
				userGroup, "writePermission", false, false);
		ProjectMetadataFactory.generateField(
				userGroup, "deletePermission", false, false);
		
		ProjectMetadataFactory.generateField(
				viewComponent, "string", false, false);
		ProjectMetadataFactory.generateField(
				viewComponent, "text", false, false);
		ProjectMetadataFactory.generateField(
				viewComponent, "dateTime", false, false);
		ProjectMetadataFactory.generateField(
				viewComponent, "date", false, false);
		ProjectMetadataFactory.generateField(
				viewComponent, "time", false, false);
		ProjectMetadataFactory.generateField(
				viewComponent, "login", false, false);
		ProjectMetadataFactory.generateField(
				viewComponent, "password", false, false);
		ProjectMetadataFactory.generateField(
				viewComponent, "email", false, false);
		ProjectMetadataFactory.generateField(
				viewComponent, "phone", false, false);
		ProjectMetadataFactory.generateField(
				viewComponent, "city", false, false);
		ProjectMetadataFactory.generateField(
				viewComponent, "zipCode", false, false);
		ProjectMetadataFactory.generateField(
				viewComponent, "country", false, false);
		ProjectMetadataFactory.generateField(
				viewComponent, "byteField", false, false);
		ProjectMetadataFactory.generateField(
				viewComponent, "charField", false, false);
		ProjectMetadataFactory.generateField(
				viewComponent, "shortField", false, false);
		ProjectMetadataFactory.generateField(
				viewComponent, "character", false, false);
		ProjectMetadataFactory.generateField(
				viewComponent, "choice", false, false);
		
		ProjectMetadataFactory.generateField(
				hiddenEntity, "content", false, false);
		
		ProjectMetadataFactory.generateField(
				postToCategory, "PostInternalId", false, false);
		ProjectMetadataFactory.generateField(
				postToCategory, "categories", false, false);
		
		ProjectMetadataFactory.generateField(
				userToUser, "UserInternalId", false, false);
		ProjectMetadataFactory.generateField(
				userToUser, "friends", false, false);
		
		
		return demact;
	}
	
}
