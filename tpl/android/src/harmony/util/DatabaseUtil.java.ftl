package ${project_namespace}.util;

import java.io.File;
import java.io.IOException;

import android.content.Context;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import ${project_namespace}.R;
import ${project_namespace}.data.base.SQLiteAdapterBase;
import ${project_namespace}.util.FolderUtil.Folder;

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
