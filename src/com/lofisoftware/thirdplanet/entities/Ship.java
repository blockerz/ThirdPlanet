package com.lofisoftware.thirdplanet.entities;

import com.lofisoftware.thirdplanet.universe.Direction;
import com.lofisoftware.thirdplanet.universe.Point;
import com.lofisoftware.thirdplanet.universe.Sector;
import com.lofisoftware.thirdplanet.universe.Tile;
import com.lofisoftware.thirdplanet.universe.UniverseFactory;

public class Ship extends Entity{
	private int desiredDirection;
	Tile north, south, east, west;
	private boolean fireLaser = false;
	private int energy, maxEnergy;
	private boolean usedEnergy;
	
	public Ship(Sector sector) {
		super(sector);
		init();
	}

	public Ship(Sector sector, Point p) {
		super(sector, p);
		init();
	}
	
	private void init() {
		setName("Ship");
		setMaxSpeed(2);	
		maxEnergy = energy = 3;
		usedEnergy = false;
	}

	public void setDirectionalTile(Tile tile, int direction){
		
		switch (direction) {
		case Direction.NORTH:
			north = tile;
			break;
		case Direction.EAST:
			east = tile;
			break;
		case Direction.SOUTH:
			south = tile;
			break;
		case Direction.WEST:
			west = tile;
			break;
		}
		
	}

	public void setRotation(int rotation) {
		super.setRotation(rotation);

		switch (rotation) {
		case Direction.NORTH:
			setTile(north);
			break;
		case Direction.EAST:
			setTile(east);
			break;
		case Direction.SOUTH:
			setTile(south);
			break;
		case Direction.WEST:
			setTile(west);
			break;
		}
		
	}

	public void moveDirection(int direction) {

		if (isAlive()) {
			
			//if (direction - getRotation() == Math.abs(180))
			//	setSpeed(getSpeed() - 1);

			if (direction == getRotation() && getSpeed() == 0)
				setSpeed(1);
			
			if (getMovesRemaining() == 0) {
				setMovesRemaining(getSpeed());
				desiredDirection = direction;
			}

			if(getMovesRemaining() > 0) {

				setMovesRemaining(getMovesRemaining()-1);
				
				// Always move forward by current speed
				switch (getRotation()) {
				case Direction.NORTH:
					moveBy(0, 1);
					break;
				case Direction.EAST:
					moveBy(1, 0);
					break;
				case Direction.SOUTH:
					moveBy(0, -1);
					break;
				case Direction.WEST:
					moveBy(-1, 0);
					break;
				}
				
			}

			if (getMovesRemaining() == 0) {
				if (fireLaser) {
					if (energy > 0) {
						UniverseFactory.createLaser(getSector(), getSectorPosition(), getRotation(), getName()).moveDirection(getRotation());
						addEnergy(-1);
						usedEnergy = true;
					}
					fireLaser = false;
				}
				setRotation(Direction.rotate90(getRotation(), desiredDirection));
				setUpdated(true);
			}
		}
	}

	public void speedUp() {
		if (isAlive()) {
			setSpeed(getSpeed()+1);
			moveDirection(getRotation());
		}
	}

	public void slowDown() {
		if (isAlive()) {
			setSpeed(getSpeed()-1);

			if (getSpeed() > 0)
				moveDirection(getRotation());
			else
				setUpdated(true);
		}
	}
	
	public void setSpeed(int speed) {
		super.setSpeed(speed);
	}

	public void fireLaser() {
		if (isAlive()) {
			fireLaser  = true;
				
			if (getSpeed() > 0)
				moveDirection(getRotation());
			else {
				if (fireLaser) {
					if (energy > 0) {
						UniverseFactory.createLaser(getSector(), getSectorPosition(), getRotation(), getName()).moveDirection(getRotation());
						addEnergy(-1);
						usedEnergy = true;
					}
					fireLaser = false;
				}
				setUpdated(true);
			}
								
		}
	}
	
	public int getEnergy() {
		return energy;
	}

	public void addEnergy(int energy) {
		this.energy = Math.max(0,Math.min(this.energy + energy,maxEnergy));
	}

	public int getMaxEnergy() {
		return maxEnergy;
	}

	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = maxEnergy;
	}

	@Override
	public void update() {
		super.update();
	}

	public boolean isUsedEnergy() {
		return usedEnergy;
	}

	public void setUsedEnergy(boolean usedEnergy) {
		this.usedEnergy = usedEnergy;
	}
	
	
	
}
