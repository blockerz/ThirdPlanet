package com.lofisoftware.thirdplanet.entities;

import com.lofisoftware.thirdplanet.messages.MessageBus;
import com.lofisoftware.thirdplanet.messages.NextTurn;
import com.lofisoftware.thirdplanet.universe.Direction;
import com.lofisoftware.thirdplanet.universe.Point;
import com.lofisoftware.thirdplanet.universe.Sector;
import com.lofisoftware.thirdplanet.universe.Tile;

public class Player extends Ship {
	
	private int turns;
	
	public Player(Sector sector) {
		super(sector);
		init();		
	}

	public Player(Sector sector, Point p) {
		super(sector, p);
		init();
	}

	private void init(){
		setName("You");
		setDirectionalTile(Tile.PLAYER_SHIP_NORTH,Direction.NORTH);
		setDirectionalTile(Tile.PLAYER_SHIP_SOUTH,Direction.SOUTH);
		setDirectionalTile(Tile.PLAYER_SHIP_EAST,Direction.EAST);
		setDirectionalTile(Tile.PLAYER_SHIP_WEST,Direction.WEST);
		setMaxHitPoints(100);
		setHitPoints(100);
		turns = 0;
	}

	public int getTurns() {
		return turns;
	}

	public void addTurn() {		
		this.turns++;
		if (!isUsedEnergy())
			addEnergy(1);
		setUsedEnergy(false);
		MessageBus.publish(new NextTurn());
	}

}
