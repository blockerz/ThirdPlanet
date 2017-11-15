package com.lofisoftware.thirdplanet.entities;

import com.lofisoftware.thirdplanet.universe.Direction;
import com.lofisoftware.thirdplanet.universe.Point;
import com.lofisoftware.thirdplanet.universe.Sector;
import com.lofisoftware.thirdplanet.universe.Tile;

public class AlienShip extends Ship {

	public AlienShip(Sector sector, Point p) {
		super(sector, p);
		init();
	}

	public AlienShip(Sector sector) {
		super(sector);
		init();
	}
	
	private void init(){
		setName("Alien Ship");
		setDirectionalTile(Tile.ALIEN_SHIP1_NORTH,Direction.NORTH);
		setDirectionalTile(Tile.ALIEN_SHIP1_SOUTH,Direction.SOUTH);
		setDirectionalTile(Tile.ALIEN_SHIP1_EAST,Direction.EAST);
		setDirectionalTile(Tile.ALIEN_SHIP1_WEST,Direction.WEST);
		setMaxEnergy(1000);
		addEnergy(1000);
	}

	public void update() {
		getEntityAI().update();
	}	
}
