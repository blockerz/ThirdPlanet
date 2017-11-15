package com.lofisoftware.thirdplanet.messages;

import com.lofisoftware.thirdplanet.entities.Entity;

public class Note extends Message {
	public Entity entity;

	public Note(Entity entity, String text){
		super(null, text);
		this.entity = entity;
	}

	@Override
	public boolean involves(Entity player) {
		if (entity == null || player == null)
			return false;
		return entity.getName().equalsIgnoreCase(player.getName());
	}

}
