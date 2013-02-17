package ${service_namespace};

import android.os.Binder;

public class ${project_name?cap_first}SyncBinder extends Binder {
	private I${project_name?cap_first}SyncService service = null; 
	  
    public ${project_name?cap_first}SyncBinder(I${project_name?cap_first}SyncService service) { 
        super(); 
        this.service = service; 
    } 
 
    public I${project_name?cap_first}SyncService getService(){ 
        return service; 
    }
}
