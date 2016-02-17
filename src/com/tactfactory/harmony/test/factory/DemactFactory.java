/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.factory;

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
     *
     * @deprecated Use {@link #getTestMetadata()} instance method
     */
    public static ApplicationMetadata generateTestMetadata() {
        return new DemactFactory().getTestMetadata();
    }

    /**
     * Generate the test metadata.
     *
     * @return The test metadata
     */
    @Override
    public ApplicationMetadata getTestMetadata() {
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

        EntityMetadata group = new EntityMetadata();
        group.setName("Group");

        EntityMetadata groupToComment = new EntityMetadata();
        groupToComment.setName("GroupToComment");

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

        EntityMetadata postToGroup = new EntityMetadata();
        postToGroup.setName("PosttoGroup");

        // Set entities attributes
        hiddenEntity.setCreateAction(false);
        hiddenEntity.setListAction(false);
        hiddenEntity.setEditAction(false);
        hiddenEntity.setShowAction(false);
        hiddenEntity.setDeleteAction(false);
        postToGroup.setInternal(true);
        postToGroup.setCreateAction(false);
        postToGroup.setListAction(false);
        postToGroup.setEditAction(false);
        postToGroup.setShowAction(false);
        postToGroup.setDeleteAction(false);
        userToUser.setInternal(true);
        userToUser.setCreateAction(false);
        userToUser.setListAction(false);
        userToUser.setEditAction(false);
        userToUser.setShowAction(false);
        userToUser.setDeleteAction(false);
        user.setCreateAction(false);
        user.setDeleteAction(false);
        comment.setEditAction(false);
        comment.setShowAction(false);
        groupToComment.setListAction(false);

        demact.getEntities().put(user.getName(), user);
        demact.getEntities().put(userToUser.getName(), userToUser);
        demact.getEntities().put(client.getName(), client);
        demact.getEntities().put(comment.getName(), comment);
        demact.getEntities().put(group.getName(), group);
        demact.getEntities().put(groupToComment.getName(), groupToComment);
        demact.getEntities().put(post.getName(), post);
        demact.getEntities().put(emptyEntity.getName(), emptyEntity);
        demact.getEntities().put(simpleEntity.getName(), simpleEntity);
        demact.getEntities().put(userGroup.getName(), userGroup);
        demact.getEntities().put(viewComponent.getName(), viewComponent);
        demact.getEntities().put(hiddenEntity.getName(), hiddenEntity);
        demact.getEntities().put(postToGroup.getName(), postToGroup);

        // Set Implements
        user.getImplementTypes().add(CLASS_CLONEABLE);

        // Set inheritance
        InheritanceMetadata userInheritance = new InheritanceMetadata();
        Map<String, EntityMetadata> userSubclasses =
                new HashMap<String, EntityMetadata>();
        userSubclasses.put(client.getName(), client);
        userInheritance.setSubclasses(userSubclasses);
        userInheritance.setType(InheritanceMode.SINGLE_TABLE);
        FieldMetadata discriminator = new FieldMetadata(user);
        discriminator.setColumnName("type");
        discriminator.setColumnDefinition("varchar");
        userInheritance.setDiscriminorColumn(discriminator);
        userInheritance.setDiscriminorIdentifier("User");
        user.setInheritance(userInheritance);

        InheritanceMetadata clientInheritance = new InheritanceMetadata();
        clientInheritance.setSuperclass(user);
        clientInheritance.setType(InheritanceMode.SINGLE_TABLE);
        clientInheritance.setDiscriminorIdentifier("Client");
        client.setInheritance(clientInheritance);


        // Set entities ids
        ProjectMetadataFactory.generateIdField(user, "id");
        ProjectMetadataFactory.generateIdField(post, "id");
        ProjectMetadataFactory.generateIdField(comment, "id");
        ProjectMetadataFactory.generateIdField(group, "id");
        ProjectMetadataFactory.generateIdField(groupToComment, "id");
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
                comment, "groups", false, false);

        ProjectMetadataFactory.generateField(
                group, "name", false, false);
        ProjectMetadataFactory.generateField(
                group, "comments", false, false);

        ProjectMetadataFactory.generateField(
                groupToComment, "displayName", false, false);
        ProjectMetadataFactory.generateField(
                groupToComment, "group", false, false);
        ProjectMetadataFactory.generateField(
                groupToComment,
                "CommentgroupsInternal",
                true,
                false,
                "Comment_groups_internal");

        ProjectMetadataFactory.generateField(
                post, "title", false, false);
        ProjectMetadataFactory.generateField(
                post, "content", false, false);
        ProjectMetadataFactory.generateField(
                post, "owner", false, false);
        ProjectMetadataFactory.generateField(
                post, "comments", false, false);
        ProjectMetadataFactory.generateField(
                post, "groups", false, false);
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
                viewComponent, "stringField", false, false);
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
                viewComponent, "booleanObject", false, false);

        ProjectMetadataFactory.generateField(
                hiddenEntity, "content", false, false);

        ProjectMetadataFactory.generateField(
                postToGroup, "PostInternalId", false, false);
        ProjectMetadataFactory.generateField(
                postToGroup, "groups", false, false);
        DemactFactory.setFieldAsId(postToGroup, "groups");
        DemactFactory.setFieldAsId(postToGroup, "PostInternalId");

        ProjectMetadataFactory.generateField(
                userToUser, "UserInternalId", false, false);
        ProjectMetadataFactory.generateField(
                userToUser, "friends", false, false);
        DemactFactory.setFieldAsId(userToUser, "friends");
        DemactFactory.setFieldAsId(userToUser, "UserInternalId");

        // Set import
        DemactFactory.addImports(user,
                CLASS_ISODATETIMEFORMAT,
                CLASS_DATETIME,
                CLASS_ENTITY,
                CLASS_TYPE,
                CLASS_COLUMN,
                CLASS_COLUMN_RESULT,
                CLASS_DISCRIMINATOR_COLUMN,
                CLASS_GENERATED_VALUE,
                CLASS_ID,
                CLASS_GENERATED_VALUE_STRATEGY,
                CLASS_INHERITANCE_TYPE,
                CLASS_MANY_TO_ONE,
                CLASS_MANY_TO_MANY,
                CLASS_TABLE,
                CLASS_INHERITANCE_MODE,
                CLASS_VIEW);

        DemactFactory.addImports(client,
                CLASS_COLUMN,
                CLASS_ENTITY);

        DemactFactory.addImports(comment,
                CLASS_ISODATETIMEFORMAT,
                CLASS_COLUMN,
                CLASS_ENTITY,
                CLASS_TABLE,
                CLASS_DATETIME,
                CLASS_GENERATED_VALUE,
                CLASS_MANY_TO_ONE,
                CLASS_ONE_TO_MANY,
                CLASS_ID,
                CLASS_GENERATED_VALUE_STRATEGY,
                CLASS_TYPE,
                CLASS_VIEW);

        DemactFactory.addImports(group,
                CLASS_COLUMN,
                CLASS_ENTITY,
                CLASS_ID,
                CLASS_GENERATED_VALUE,
                CLASS_GENERATED_VALUE_STRATEGY,
                CLASS_ONE_TO_MANY);

        DemactFactory.addImports(groupToComment,
                CLASS_COLUMN,
                CLASS_ENTITY,
                CLASS_ID,
                CLASS_MANY_TO_ONE,
                CLASS_VIEW);

        DemactFactory.addImports(post,
                CLASS_ISODATETIMEFORMAT,
                CLASS_DATETIME,
                CLASS_COLUMN,
                CLASS_ENTITY,
                CLASS_TABLE,
                CLASS_TYPE,
                CLASS_ID,
                CLASS_MANY_TO_ONE,
                CLASS_MANY_TO_MANY,
                CLASS_ONE_TO_MANY,
                CLASS_GENERATED_VALUE,
                CLASS_GENERATED_VALUE_STRATEGY);

        DemactFactory.addImports(emptyEntity,
                CLASS_ENTITY);

        DemactFactory.addImports(simpleEntity,
                CLASS_ENTITY,
                CLASS_COLUMN,
                CLASS_GENERATED_VALUE,
                CLASS_GENERATED_VALUE_STRATEGY,
                CLASS_ID,
                CLASS_TYPE);

        DemactFactory.addImports(userGroup,
                CLASS_ENTITY,
                CLASS_COLUMN,
                CLASS_ID,
                CLASS_GENERATED_VALUE,
                CLASS_GENERATED_VALUE_STRATEGY);

        DemactFactory.addImports(viewComponent,
                CLASS_ISODATETIMEFORMAT,
                CLASS_COLUMN,
                CLASS_ENTITY,
                CLASS_ID,
                CLASS_GENERATED_VALUE,
                CLASS_GENERATED_VALUE_STRATEGY,
                CLASS_TYPE,
                CLASS_DATETIME);

        DemactFactory.addImports(hiddenEntity,
                CLASS_DATETIME,
                CLASS_ARRAYLIST,
                CLASS_TYPE,
                CLASS_COLUMN,
                CLASS_GENERATED_VALUE,
                CLASS_GENERATED_VALUE_STRATEGY,
                CLASS_ID,
                CLASS_MANY_TO_ONE,
                CLASS_MANY_TO_MANY,
                CLASS_ONE_TO_MANY,
                CLASS_VIEW,
                CLASS_TABLE);

        for (EntityMetadata entity : demact.getEntities().values()) {
            if (!entity.isInternal()) {
                DemactFactory.addImports(
                        entity,
                        CLASS_SERIALIZABLE,
                        CLASS_PARCELABLE,
                        CLASS_PARCEL);

                DemactFactory.addImplements(
                        entity,
                        CLASS_SERIALIZABLE,
                        CLASS_PARCELABLE);

                DemactFactory.addImports(
                        entity,
                        CLASS_LIST,
                        CLASS_ARRAYLIST);
            }
        }

        return demact;
    }

}
