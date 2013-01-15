package com.tactfactory.mda.test.demact.fixture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.joda.time.DateTime;
import org.yaml.snakeyaml.Yaml;

import com.tactfactory.mda.test.DataManager;
import com.tactfactory.mda.test.FixtureBase;
import com.tactfactory.mda.test.demact.entity.User;
import com.tactfactory.mda.utils.FileUtils;

public class UserDataLoader extends FixtureBase {
	HashMap<String, User> users = new HashMap<String, User>();
	
	/**
	 * @see com.tactfactory.mda.test.FixtureBase#getModelFixture(java.lang.String)
	 */
	@Override
	public Object getModelFixture(String id) {
		return this.users.get(id);
	}

	/**
	 * @see com.tactfactory.mda.test.FixtureBase#getModelFixtures()
	 */
	@Override
	public void getModelFixtures() {
		User user = null;
		
		// XML Loader
		try {
			String entityName = "User";
			String currentDir = new File(".").getAbsolutePath();

			SAXBuilder builder = new SAXBuilder();		// Make engine
			File xmlFile = new File(currentDir + "/src/com/tactfactory/mda/test/demact/fixture/" + entityName + ".xml");
			Document doc = (Document) builder.build(xmlFile); 	// Load XML File
			final Element rootNode = doc.getRootElement(); 			// Load Root element
			//final Namespace ns = rootNode.getNamespace("android");	// Load Name space (required for manipulate attributes)

			// Find Application Node
			List<Element> entities = rootNode.getChildren(entityName); 	// Find a element
			if (entities != null) {
				for (Element element : entities) {
					user = new User();
					user.setId(Integer.parseInt(element.getChildText("id")));
					user.setFirstname((String)element.getChildText("firstname"));
					user.setLastname((String)element.getChildText("lastname"));
					user.setLogin((String)element.getChildText("login"));
					user.setPassword((String)element.getChildText("password"));
					user.setCreatedAt(new DateTime());
					users.put((String)element.getAttributeValue("id") , user);
				}
			}
		} catch (IOException io) {
			io.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		
//		// YAML Loader
//		try {
//			String entityName = "User";
//			String currentDir = new File(".").getAbsolutePath();
//			Yaml yaml = new Yaml();
//			FileInputStream fileInputStream = new FileInputStream(new File(currentDir + "/src/com/tactfactory/mda/test/demact/fixture/" + entityName + ".yml"));
//			
//			Map<?, ?> map = (Map<?, ?>) yaml.load(fileInputStream);
//			Map<?, ?> listEntities = (Map<?, ?>) map.get(entityName);
//			for (Object name : listEntities.keySet()) {
//				try {
//					Map<?, ?> columns = (Map<?, ?>) listEntities.get(name);
//					user = new User();
//					user.setId((Integer)columns.get("id"));
//					user.setFirstname((String)columns.get("firstname"));
//					user.setLastname((String)columns.get("lastname"));
//					user.setLogin((String)columns.get("login"));
//					user.setPassword((String)columns.get("password"));
//					user.setCreatedAt(new DateTime());
//					users.put((String)name, user);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}

//		// Manual Loader
//		user = new User();
//		user.setId(0);
//		user.setFirstname("Dupond");
//		user.setLastname("Dupont");
//		user.setLogin("dup");
//		user.setPassword("password");
//		user.setCreatedAt(new DateTime());
//		users.put(user.getLastname(), user);
//
//		user = new User();
//		user.setId(1);
//		user.setFirstname("Haddock");
//		user.setLastname("Archibald");
//		user.setLogin("had");
//		user.setPassword("password");
//		user.setCreatedAt(new DateTime());
//		users.put(user.getLastname(), user);
//
//		user = new User();
//		user.setId(2);
//		user.setFirstname("Tournesol");
//		user.setLastname("Tryphon");
//		user.setLogin("tou");
//		user.setPassword("password");
//		user.setCreatedAt(new DateTime());
//		users.put(user.getLastname(), user);
	}

	/**
	 * @see com.tactfactory.mda.test.FixtureBase#load(DataManager manager)
	 */
	@Override
	public void load(DataManager manager) {
		for (User user : this.users.values()) {
			manager.persist(user);
		}

		manager.flush();
	}

	/**
	 * @see com.tactfactory.mda.test.FixtureBase#getOrder()
	 */
	@Override
	public int getOrder() {
		return 0;
	}

}
