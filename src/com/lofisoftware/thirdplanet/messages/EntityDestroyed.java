package com.lofisoftware.thirdplanet.messages;

import com.lofisoftware.thirdplanet.entities.Entity;
import com.lofisoftware.thirdplanet.universe.Sector;

public class EntityDestroyed extends Message {
	public Entity destroyer;
	public Entity destroyed;
	public Sector sector;
	
	public EntityDestroyed(Sector sector, Entity destroyer, Entity destroyed, String text) {
		super(null, text);
		this.sector = sector;
		this.destroyer = destroyer;
		this.destroyed = destroyed;
	}

	@Override
	public boolean involves(Entity entity) {
		if (entity == null || destroyer == null || destroyed == null)
			return false;
		return (entity.getName().equalsIgnoreCase(destroyer.getName()) || entity.getName().equalsIgnoreCase(destroyed.getName()));
	
	}

}
