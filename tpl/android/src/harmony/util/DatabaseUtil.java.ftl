package ${project_namespace}.harmony.util;

import java.io.File;

import android.content.Context;

import com.google.common.base.Strings;
import com.google.common.io.Files;

public class DatabaseUtil {
	
	public static void importDB(Context context, File source, String databaseName, boolean removeSource) throws Exception {
		if (Strings.isNullOrEmpty(databaseName) || 
				source == null || 
				context ==  null) {
			throw new Exception("Invalid parameter");
		}
		
		if (source.exists()) {
			String path = context.getDatabasePath(databaseName).getPath();
			File destination = new File(path);
			
			Files.copy(source, destination);
			
			if (removeSource) {
				source.delete();
			}
		}
	}
	
	public static void exportDB(Context context, File destination, String databaseName) throws Exception {
		if (Strings.isNullOrEmpty(databaseName) || 
				destination == null || 
				context ==null) {
			throw new Exception("Invalid parameter");
		}

		String path = context.getDatabasePath(databaseName).getPath();
		File source = new File(path);
		
		Files.copy(source, destination);
		
	}

}
