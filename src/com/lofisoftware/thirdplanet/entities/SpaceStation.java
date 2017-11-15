package com.lofisoftware.thirdplanet.entities;

import com.lofisoftware.thirdplanet.universe.Point;
import com.lofisoftware.thirdplanet.universe.Sector;

public class SpaceStation extends LargeEntity {

	public SpaceStation(Sector sector, int width, int height) {
		super(sector, width, height);
		init();
	}

	public SpaceStation(Sector sector, Point p, int width, int height) {
		super(sector, p, width, height);
		init();
	}

	private void init() {
		setDestructible(false);
		setAttackPower(100);
		setMaxHitPoints(1000);
		setHitPoints(1000);
		setMaxSpeed(0);
		setName("Beacon");
		setSpeed(0);
		setVisionRadius(20);
	}
}
