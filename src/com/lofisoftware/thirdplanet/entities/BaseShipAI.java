package com.lofisoftware.thirdplanet.entities;

import com.lofisoftware.thirdplanet.universe.Point;
import com.lofisoftware.thirdplanet.universe.Sector;

public class BaseShipAI extends EntityAI {

	public BaseShipAI(Entity entity) {
		super(entity);

	}
	
	public void onEnter(Point p, Sector sector){

	    	entity.setSectorPosition(p);
	}

}
