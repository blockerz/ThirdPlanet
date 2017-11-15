package com.lofisoftware.thirdplanet.entities;

import com.lofisoftware.thirdplanet.universe.Point;
import com.lofisoftware.thirdplanet.universe.Sector;
import com.lofisoftware.thirdplanet.universe.Tile;
import com.lofisoftware.thirdplanet.util.Line;

public class EntityAI {

	protected Entity entity;

	public EntityAI(Entity entity){
		this.entity = entity;
		this.entity.setEntityAI(this);
	}

	public void onEnter(Point p, Sector sector) { }
	
	public void update () { }

	public boolean canSee(int wx, int wy) {
		
		int nebula = 0;
		int nebula_visibility = 4;
     
        if ((entity.getSectorPosition().x()-wx)*(entity.getSectorPosition().x()-wx) + (entity.getSectorPosition().y()-wy)*(entity.getSectorPosition().y()-wy) > entity.getVisionRadius()*entity.getVisionRadius())
            return false;
     
        for (Point p : new Line(entity.getSectorPosition().x(), entity.getSectorPosition().y(), wx, wy)){
        	Entity e = entity.getSector().getEntity(p);
            if (!entity.getSector().getTile(p).isSolid() && (e == null || !e.getTile().isSolid() || e==entity) || p.x() == wx && p.y() == wy) {
        	            	
            	if (entity.getSector().getTile(p) == Tile.NEBULA)
            		nebula++;
            	else 
            		nebula = 0;
            	
            	if (nebula < nebula_visibility) 
            		continue;
            }
         
            return false;
        }
     
        return true;
	}
	
	public void wander(){
	    int mx = (int)(Math.random() * 3) - 1;
	    int my = (int)(Math.random() * 3) - 1;
	    entity.moveBy(mx, my);
	}
	
}
