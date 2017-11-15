package com.lofisoftware.thirdplanet.messages;

import com.lofisoftware.thirdplanet.entities.Entity;
import com.lofisoftware.thirdplanet.universe.Sector;

public class EntityDamaged extends Message {

	public Entity damager;
	public Entity damagee;
	public Sector sector;
	
	public EntityDamaged(Sector sector, Entity destroyer, Entity destroyed, String text) {
		super(null, text);
		this.sector = sector;
		this.damager = destroyer;
		this.damagee = destroyed;
	}

	@Override
	public boolean involves(Entity entity) {
		if (entity == null || damager == null || damagee == null)
			return false;
		return (entity.getName().equalsIgnoreCase(damagee.getName()) || entity.getName().equalsIgnoreCase(damager.getName()));
	}

}
