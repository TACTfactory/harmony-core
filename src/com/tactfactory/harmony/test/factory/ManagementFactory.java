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

public class ManagementFactory extends ProjectMetadataFactory {

    /**
     * Generate the test metadata.
     *
     * @return The test metadata
     *
     * @deprecated Use {@link #getTestMetadata()} instance method
     */
    public static ApplicationMetadata generateTestMetadata() {
        return new ManagementFactory().getTestMetadata();
    }

    /**
     * Generate the test metadata.
     *
     * @return The test metadata
     */
    @Override
    public ApplicationMetadata getTestMetadata() {
        ApplicationMetadata management = new ApplicationMetadata();
        management.setName("Management");
        management.setProjectNameSpace("com/tactfactory/harmony/test/management");

        // Entities
        EntityMetadata address = ManagementFactory.addEntity("Address", management);

        EntityMetadata company = ManagementFactory.addEntity("Company", management);

        EntityMetadata person = ManagementFactory.addEntity("Person", management);

        EntityMetadata worker = ManagementFactory.addEntity("Worker", management);

        EntityMetadata manager = ManagementFactory.addEntity("Manager", management);

        EntityMetadata day = ManagementFactory.addEntity("Day", management);

        EntityMetadata furniture = ManagementFactory.addEntity("Furniture", management);

        EntityMetadata tv = ManagementFactory.addEntity("TV", management);

        EntityMetadata projector = ManagementFactory.addEntity("Projector", management);

        EntityMetadata office = ManagementFactory.addEntity("Office", management);

        EntityMetadata personToAddress = ManagementFactory.addEntity("PersontoAddress", management);
        personToAddress.setInternal(true);
        personToAddress.setCreateAction(false);
        personToAddress.setListAction(false);
        personToAddress.setEditAction(false);
        personToAddress.setShowAction(false);
        personToAddress.setDeleteAction(false);

        EntityMetadata personToDay = ManagementFactory.addEntity("PersontoDay", management);
        personToDay.setInternal(true);
        personToDay.setCreateAction(false);
        personToDay.setListAction(false);
        personToDay.setEditAction(false);
        personToDay.setShowAction(false);
        personToDay.setDeleteAction(false);

//        EntityMetadata office = ManagementFactory.addEntity("Office", management);

        // Set Implements
        ManagementFactory.addImports(address,
                CLASS_COLUMN,
                CLASS_ENTITY,
                CLASS_GENERATED_VALUE,
                CLASS_ID,
                CLASS_MANY_TO_MANY,
                CLASS_GENERATED_VALUE_STRATEGY);
        ManagementFactory.addImports(company,
                CLASS_COLUMN,
                CLASS_ENTITY,
                CLASS_GENERATED_VALUE,
                CLASS_ID,
                CLASS_ONE_TO_MANY,
                CLASS_GENERATED_VALUE_STRATEGY);
        ManagementFactory.addImports(day,
                CLASS_COLUMN,
                CLASS_ENTITY,
                CLASS_ID,
                CLASS_ONE_TO_ONE);
        ManagementFactory.addImports(furniture,
                CLASS_ISODATETIMEFORMAT,
                CLASS_DATETIME,
                CLASS_COLUMN,
                CLASS_ENTITY,
                CLASS_ID,
                CLASS_INHERITANCE_TYPE,
                CLASS_INHERITANCE_MODE);
        ManagementFactory.addImports(manager,
                CLASS_COLUMN,
                CLASS_ENTITY);
        ManagementFactory.addImports(office,
                CLASS_COLUMN,
                CLASS_ENTITY,
                CLASS_ID,
                CLASS_ONE_TO_ONE);
        ManagementFactory.addImports(person,
                CLASS_COLUMN,
                CLASS_TYPE,
                CLASS_COLUMN_RESULT,
                CLASS_ENTITY,
                CLASS_ID,
                CLASS_INHERITANCE_TYPE,
                CLASS_MANY_TO_MANY,
                CLASS_MANY_TO_ONE,
                CLASS_INHERITANCE_MODE);
        ManagementFactory.addImports(projector,
                CLASS_COLUMN,
                CLASS_ENTITY,
                CLASS_TYPE);
        ManagementFactory.addImports(tv,
                CLASS_COLUMN,
                CLASS_ENTITY);
        ManagementFactory.addImports(worker,
                CLASS_COLUMN,
                CLASS_ENTITY,
                CLASS_MANY_TO_ONE);

        for (EntityMetadata entity : management.getEntities().values()) {
            if (!entity.isInternal()) {
                ManagementFactory.addImports(
                        entity,
                        CLASS_LIST,
                        CLASS_ARRAYLIST,
                        CLASS_SERIALIZABLE,
                        CLASS_PARCELABLE,
                        CLASS_PARCEL);

                ManagementFactory.addImplements(
                        entity,
                        CLASS_SERIALIZABLE,
                        CLASS_PARCELABLE);
            }
        }

        // Set inheritance
        InheritanceMetadata personInheritance = new InheritanceMetadata();
        Map<String, EntityMetadata> personSubclasses =
                new HashMap<String, EntityMetadata>();
        personSubclasses.put(worker.getName(), worker);
        personSubclasses.put(manager.getName(), manager);
        personInheritance.setSubclasses(personSubclasses);
        personInheritance.setType(InheritanceMode.SINGLE_TABLE);
        FieldMetadata discriminator = new FieldMetadata(person);
        discriminator.setColumnName("inheritance_type");
        discriminator.setColumnDefinition("VARCHAR");
        personInheritance.setDiscriminorColumn(discriminator);
        person.setInheritance(personInheritance);

        InheritanceMetadata workerInheritance = new InheritanceMetadata();
        workerInheritance.setSuperclass(person);
        workerInheritance.setType(InheritanceMode.SINGLE_TABLE);
        workerInheritance.setDiscriminorIdentifier("Worker");
        worker.setInheritance(workerInheritance);

        InheritanceMetadata managerInheritance = new InheritanceMetadata();
        managerInheritance.setSuperclass(person);
        managerInheritance.setType(InheritanceMode.SINGLE_TABLE);
        managerInheritance.setDiscriminorIdentifier("Manager");
        manager.setInheritance(managerInheritance);

        InheritanceMetadata furnitureInheritance = new InheritanceMetadata();
        Map<String, EntityMetadata> furnitureSubclasses =
                new HashMap<String, EntityMetadata>();
        furnitureSubclasses.put(projector.getName(), projector);
        furnitureSubclasses.put(tv.getName(), tv);
        furnitureInheritance.setSubclasses(furnitureSubclasses);
        furnitureInheritance.setType(InheritanceMode.JOINED);
        discriminator = new FieldMetadata(person);
        discriminator.setColumnName("inheritance_type");
        discriminator.setColumnDefinition("VARCHAR");
        furnitureInheritance.setDiscriminorColumn(discriminator);
        furniture.setInheritance(furnitureInheritance);

        InheritanceMetadata projectorInheritance = new InheritanceMetadata();
        projectorInheritance.setSuperclass(furniture);
        projectorInheritance.setType(InheritanceMode.JOINED);
        projectorInheritance.setDiscriminorIdentifier("Projector");
        projector.setInheritance(projectorInheritance);

        InheritanceMetadata tvInheritance = new InheritanceMetadata();
        tvInheritance.setSuperclass(furniture);
        tvInheritance.setType(InheritanceMode.JOINED);
        tvInheritance.setDiscriminorIdentifier("TV");
        tv.setInheritance(tvInheritance);

        // Set entities fields
        ProjectMetadataFactory.generateField(
                address, "id", false, true);
        ProjectMetadataFactory.generateField(
                address, "street", false, false);
        ProjectMetadataFactory.generateField(
                address, "city", false, false);
        ProjectMetadataFactory.generateField(
                address, "country", false, false);
        ProjectMetadataFactory.generateField(
                address, "streetNumber", true, false);
        ProjectMetadataFactory.generateField(
                address, "zipcode", false, false);
        ProjectMetadataFactory.generateField(
                address, "inhabitants", false, false);

        ProjectMetadataFactory.generateField(
                company, "id", false, true);
        ProjectMetadataFactory.generateField(
                company, "name", false, false);
        ProjectMetadataFactory.generateField(
                company, "employees", false, false);

        ProjectMetadataFactory.generateField(
                day, "identifier", false, true);
        ProjectMetadataFactory.generateField(
                day, "theDay", false, false);
        ProjectMetadataFactory.generateField(
                day, "month", false, false);
        ProjectMetadataFactory.generateField(
                day, "year", false, false);
        ProjectMetadataFactory.generateField(
                day, "publicHoliday", false, false);
        ProjectMetadataFactory.generateField(
                day, "officeToClean", true, false);

        ProjectMetadataFactory.generateField(
                furniture, "id", false, true);
        ProjectMetadataFactory.generateField(
                furniture, "price", false, false);
        ProjectMetadataFactory.generateField(
                furniture, "purchaseDate", false, false);

        ProjectMetadataFactory.generateField(
                manager, "salary", true, false);

        ProjectMetadataFactory.generateField(
                office, "floor", false, true);
        ProjectMetadataFactory.generateField(
                office, "name", false, true);
        ProjectMetadataFactory.generateField(
                office, "cleaningDay", true, false);
        ProjectMetadataFactory.generateField(
                office, "furniture", true, false);

        ProjectMetadataFactory.generateField(
                person, "firstname", false, false);
        ProjectMetadataFactory.generateField(
                person, "lastname", false, false);
        ProjectMetadataFactory.generateField(
                person, "name", false, false, "firstname || ' ' || lastname");
        ProjectMetadataFactory.generateField(
                person, "phoneNumber", false, false);
        ProjectMetadataFactory.generateField(
                person, "company", false, false);
        ProjectMetadataFactory.generateField(
                person, "addresses", false, false);
        ProjectMetadataFactory.generateField(
                person, "holidays", false, false);

        ProjectMetadataFactory.generateField(
                projector, "lensSize", false, false);
        ProjectMetadataFactory.generateField(
                projector, "lampType", false, false);

        ProjectMetadataFactory.generateField(
                tv, "brand", false, false);
        ProjectMetadataFactory.generateField(
                tv, "width", false, false);
        ProjectMetadataFactory.generateField(
                tv, "height", false, false);
        ProjectMetadataFactory.generateField(
                worker, "manager", true, false);

        ProjectMetadataFactory.generateField(
                personToAddress, "addresses", false, false);
        ProjectMetadataFactory.generateField(
                personToAddress, "inhabitants", false, false);

        ProjectMetadataFactory.generateField(
                personToDay, "holidays", false, false);
        ProjectMetadataFactory.generateField(
                personToDay, "PersonInternalId", false, false);

        ManagementFactory.setFieldAsId(address, "id");
        ManagementFactory.setFieldAsId(company, "id");
        ManagementFactory.setFieldAsId(day, "identifier");
        ManagementFactory.setFieldAsId(furniture, "id");
        ManagementFactory.setFieldAsId(office, "floor", "name");
        ManagementFactory.setFieldAsId(person, "firstname", "lastname");
        ManagementFactory.setFieldAsId(personToAddress, "addresses", "inhabitants");
        ManagementFactory.setFieldAsId(personToDay, "holidays", "PersonInternalId");

        return management;
    }
}
