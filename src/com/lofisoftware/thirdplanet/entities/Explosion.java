package com.lofisoftware.thirdplanet.entities;

import com.lofisoftware.thirdplanet.universe.Point;
import com.lofisoftware.thirdplanet.universe.Sector;

public class Explosion extends Entity {

	public Explosion(Sector sector, Point p) {
		super(sector, p);
		setName("Explosion");
		setMovesRemaining(30);
	}
	
	public void moveDirection(int direction) {

		setMovesRemaining(getMovesRemaining() - 1);
		
		if (getMovesRemaining() <= 0)
			getSector().remove(this);
	}

}
