package com.lofisoftware.thirdplanet.entities;

import com.lofisoftware.thirdplanet.universe.Point;
import com.lofisoftware.thirdplanet.universe.Sector;

public class ProjectileAI extends EntityAI {

	public ProjectileAI(Entity entity) {
	    super(entity);
	    }

	public void onEnter(Point p, Sector sector){
	    if (sector.canEnter(p)){
	    	entity.setSectorPosition(p);
	    } 
	    else {
	    	Entity e = sector.getEntity(p);
	    	if (e!=null && e.getTile().isSolid() && entity.isAlive()) {
	    		entity.modifyHitPoints(e,-entity.getAttackPower());
	    		
	    		if(entity.isDestructible()){
	    			entity.setHitPoints(-1);
	    			sector.remove(entity);
	    		}
	    	}
	    }
	}
}
