package com.lofisoftware.thirdplanet.entities;

import com.lofisoftware.thirdplanet.universe.Direction;
import com.lofisoftware.thirdplanet.universe.Point;
import com.lofisoftware.thirdplanet.universe.Sector;
import com.lofisoftware.thirdplanet.universe.UniverseFactory;

public class BaseShip extends LargeEntity {

	public BaseShip(Sector sector, int width, int height) {
		super(sector, width, height);
	}

	public BaseShip(Sector sector, Point p, int width, int height) {
		super(sector, p, width, height);
	}
	
	public void update() {
		super.update();
	}

	public void fireLaser(int direction) {
		if (isAlive()) {
			Point p = getSectorPosition();
			int dx = 0;
			int dy = 0;
			
			switch (direction){
			case Direction.NORTH:
				dx = getWidth()/2;
				dy = getHeight();
				break;
			case Direction.SOUTH:
				dx = getWidth()/2;
				break;
			case Direction.WEST:				
				dy = getHeight()/2;
				break;
			case Direction.EAST:
				dx = getWidth();
				dy = getHeight()/2;
				break;			
			}
				
			p = p.plus(dx , dy);
				
			UniverseFactory.createLaser(getSector(), p, direction, getName()).moveDirection(getRotation());											
		}
	}
}
