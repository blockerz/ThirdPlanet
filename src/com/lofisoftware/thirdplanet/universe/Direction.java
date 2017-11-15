package com.lofisoftware.thirdplanet.universe;

import com.badlogic.gdx.math.MathUtils;

public class Direction {

	public static final int NORTH = 0;
	public static final int EAST = 90;
	public static final int SOUTH = 180;
	public static final int WEST = 270;
	
	public static int randomDirection(){
		
		switch(MathUtils.random(0, 3)){
		case 0:
			return Direction.SOUTH;
		case 1:
			return Direction.NORTH;
		case 2:
			return Direction.WEST;
		case 3:
			return Direction.EAST;
		}
		return 0;
	}
	
	public static int opposite(int direction) {
		int opposite;
		
		switch(direction) {
		case Direction.NORTH:
			opposite = Direction.SOUTH;
			break;
		case Direction.SOUTH:
			opposite = Direction.NORTH;
			break;
		case Direction.EAST:
			opposite = Direction.WEST;
			break;
		case Direction.WEST:
			opposite = Direction.EAST;
			break;
			default:
				opposite = -1;
		}
		return opposite;		
	}
	
	public static int rotate90(int currentDirection, int desiredDirection) {
		
		int rotate = currentDirection;
		
		switch (currentDirection) {
		case Direction.NORTH:
			switch (desiredDirection) {
			case Direction.SOUTH:
			case Direction.EAST:
				rotate = Direction.EAST;
				break;
			case Direction.WEST:
				rotate = Direction.WEST;
				break;
			}
			break;
		case Direction.EAST:
			switch (desiredDirection) {
			case Direction.WEST:
			case Direction.NORTH:
				rotate = Direction.NORTH;
				break;
			case Direction.SOUTH:
				rotate = Direction.SOUTH;
				break;
			}
			break;
		case Direction.SOUTH:
			switch (desiredDirection) {
			case Direction.NORTH:
			case Direction.EAST:
				rotate = Direction.EAST;
				break;
			case Direction.WEST:
				rotate = Direction.WEST;
				break;
			}
			break;
		case Direction.WEST:
			switch (desiredDirection) {
			case Direction.EAST:
			case Direction.NORTH:
				rotate = Direction.NORTH;
				break;
			case Direction.SOUTH:
				rotate = Direction.SOUTH;
				break;
			}
			break;
		}
		
		return rotate;
	}	
	
	public static int getXYDirection(int mx, int my){
		
		if (mx == 0 && my > 0)
			return Direction.NORTH;
		
		if (mx == 0 && my < 0)
			return Direction.SOUTH;
		
		if (my==0 && mx < 0)
			return Direction.WEST;
		
		if (my==0 && mx > 0)
			return Direction.EAST;
		
		return -1;
	}

}
