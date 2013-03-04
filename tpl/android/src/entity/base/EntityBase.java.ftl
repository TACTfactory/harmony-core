package ${entity_namespace}.base;

import java.io.Serializable;

import org.joda.time.DateTime;

public class EntityBase implements Cloneable, Serializable {
	public Integer serverId;
	public int id;
	public boolean	sync_dtag = false;
	public DateTime 	sync_uDate = new DateTime();
	
	
	
	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public EntityBase clone() throws CloneNotSupportedException {
		EntityBase entity = new EntityBase();
		entity.id = this.id;
		entity.serverId = this.serverId;
		entity.sync_dtag = this.sync_dtag;
		entity.sync_uDate = new DateTime(this.sync_uDate);
		
		return entity;
	}
	
	public int getId(){ return this.id;}
	public void setId(int id){ this.id = id; }
	public Integer getServerId(){ return this.serverId;}
	public void setServerId(Integer serverid){ this.serverId = serverid;}
	public boolean isSync_dtag(){ return this.sync_dtag;}
	public void setSync_dtag(boolean dtag){ this.sync_dtag = dtag;}
	public DateTime getSync_uDate(){ return this.sync_uDate;}
	public void setSync_uDate(DateTime sync_uDate){ this.sync_uDate = sync_uDate;}
}