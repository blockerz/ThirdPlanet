package com.lofisoftware.thirdplanet.entities;

import com.lofisoftware.thirdplanet.universe.Direction;
import com.lofisoftware.thirdplanet.universe.Point;
import com.lofisoftware.thirdplanet.universe.Sector;

public class Asteroid extends Entity {	

	public Asteroid(Sector sector, Point p) {
		super(sector, p);
		init();
	}

	public Asteroid(Sector sector) {
		super(sector);
		init();
	}

	
	public void init() {
		setName("Asteroid");
		setDestructible(true);
		setMaxHitPoints(20);
		setHitPoints(20);
		setAttackPower(20);
	}
	
	public void update() {
		if (getRotation() == Direction.EAST && getSectorPosition().x() >= getSector().getWidth()-1)
			moveBy(1-getSector().getWidth(),0);
		else if (getRotation() == Direction.WEST && getSectorPosition().x() <= 0)
			moveBy(getSector().getWidth()-1,0);
		else if (getRotation() == Direction.NORTH && getSectorPosition().y() >= getSector().getHeight()-1)
			moveBy(0,1-getSector().getHeight());
		else if (getRotation() == Direction.SOUTH && getSectorPosition().y() <= 0)
			moveBy(0,getSector().getHeight()-1);
		else
			moveDirection(getRotation());
	}
}
