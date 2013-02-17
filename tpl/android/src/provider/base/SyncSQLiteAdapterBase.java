package ${data_namespace}.base;

import android.content.Context;
import android.database.Cursor;

import ${entity_namespace}.base.EntityBase;

public abstract class SyncSQLiteAdapterBase<T extends EntityBase> extends SQLiteAdapterBase<T>{
	public static final String COL_SERVERID = "serverId";
	public static final String COL_SYNC_DTAG = "sync_dtag";
	public static final String COL_SYNC_UDATE = "sync_uDate";
	
	
	protected SyncSQLiteAdapterBase(Context ctx) {
		super(ctx);
	}

	
	public T getByServerID(Integer serverId){
		Cursor c = this.query(this.getCols(), COL_SERVERID+"=? ", new String[]{String.valueOf(serverId)}, null, null, null);
		if(c.getCount()!=0)
			c.moveToFirst();
		T result = this.cursorToItem(c);
		c.close();
		
		return result;
	}
}