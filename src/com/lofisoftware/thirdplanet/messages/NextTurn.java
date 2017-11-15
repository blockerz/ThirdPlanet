package com.lofisoftware.thirdplanet.messages;

import com.lofisoftware.thirdplanet.entities.Entity;

public class NextTurn extends Message {

	public NextTurn() {
		super(null, "");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean involves(Entity entity) {
		return false;
	}

}
