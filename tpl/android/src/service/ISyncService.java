package ${local_namespace};

public interface I${project_name?cap_first}SyncService {
	public void addListener(I${project_name?cap_first}SyncListener listener); 
    public void removeListener(I${project_name?cap_first}SyncListener listener);
}
