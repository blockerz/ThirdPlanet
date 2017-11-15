package com.lofisoftware.thirdplanet.entities;

import com.lofisoftware.thirdplanet.universe.Direction;
import com.lofisoftware.thirdplanet.universe.Point;
import com.lofisoftware.thirdplanet.universe.Sector;

public class Projectile extends Entity{
	
	private int range;

	public Projectile(Sector sector, Point p) {
		super(sector, p);
		init();
	}

	public Projectile(Sector sector) {
		super(sector);
		init();
	}
	
	

	private void init() {
		setName("Projectile");
		setAttackPower(5);
		range = 20;		
	}

	public void update() {
		
		if (range < 0)
			getSector().remove(this);
		
		if (getRotation() == Direction.EAST && getSectorPosition().x() >= getSector().getWidth()-1)
			getSector().remove(this);
		else if (getRotation() == Direction.WEST && getSectorPosition().x() <= 0)
			getSector().remove(this);
		else if (getRotation() == Direction.NORTH && getSectorPosition().y() >= getSector().getHeight()-1)
			getSector().remove(this);
		else if (getRotation() == Direction.SOUTH && getSectorPosition().y() <= 0)
			getSector().remove(this);
		else if (isAlive()){			
			moveDirection(getRotation());
			range--;
		}
	}
	
	
	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}
		
}
