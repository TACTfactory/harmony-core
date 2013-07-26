<@header?interpret />
package ${entity_namespace}.base;

import java.io.Serializable;

import org.joda.time.DateTime;

public class EntityBase implements Cloneable, Serializable {
	/** Server ID. */
	public Integer serverId;
	/** Entity ID. */
	public int id;
	/** sync_dtag. */
	public boolean	sync_dtag = false;
	/** sync_date. */
	public DateTime 	sync_uDate = new DateTime();
	
	
	
	/**
	 * @see java.lang.Object#clone()
	 * @return A new EntityBase which is the same 
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
	
	/**
	 * Get the EntityBase id.
	 * @return The EntityBase id
	 */
	public int getId(){ return this.id; }
	/**
	 * Set the EntityBase id.
	 * @param id The new EntityBase id
	 */
	public void setId(int id){ this.id = id; }
	/**
	 * Get the EntityBase server id.
	 * @return The EntityBase server id
	 */
	public Integer getServerId(){ return this.serverId; }
	/**
	 * Set the EntityBase id.
	 * @param serverid The EntityBase server id
	 */
	public void setServerId(Integer serverid){ this.serverId = serverid; }
	/**
	 * Checks if the EntityBase is sync.
	 * @return true if the EntityBase is sync, false otherwise
	 */
	public boolean isSync_dtag(){ return this.sync_dtag; }
	/**
	 * Set the EntityBase dtag.
	 * @param dtag The EntityBase dtag
	 */
	public void setSync_dtag(boolean dtag){ this.sync_dtag = dtag; }
	/**
	 * Get the EntityBase uDate.
	 * @return The EntityBase uDate
	 */
	public DateTime getSync_uDate(){ return this.sync_uDate; }
	/**
	 * Set the EntityBase uDate.
	 * @param sync_uDate The EntityBase uDate
	 */
	public void setSync_uDate(DateTime sync_uDate){ 
		this.sync_uDate = sync_uDate;
	}
}
