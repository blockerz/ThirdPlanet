package com.lofisoftware.thirdplanet.entities;

import com.badlogic.gdx.math.MathUtils;
import com.lofisoftware.thirdplanet.universe.Direction;
import com.lofisoftware.thirdplanet.universe.Point;
import com.lofisoftware.thirdplanet.universe.Sector;
import com.lofisoftware.thirdplanet.universe.Tile;
import com.lofisoftware.thirdplanet.util.AStarPathFinder;
import com.lofisoftware.thirdplanet.util.Path;
import com.lofisoftware.thirdplanet.util.PathFinder;

public class AlienShipAI extends EntityAI {

	
	private PathFinder finder;
	private Path path;
	
	public AlienShipAI(Entity entity) {
		super(entity);
		finder = new AStarPathFinder(entity.getSector(), 500, false);
	}

	public void onEnter(Point p, Sector sector) {
		if (sector.canEnter(p)) {
			boolean laser = false;
			if (sector.getEntity(p) != null)
				if (sector.getEntity(p).getTile() == Tile.LASER_EAST_WEST || sector.getEntity(p).getTile() == Tile.LASER_NORTH_SOUTH)
					laser = true;
		if (!laser)
			entity.setSectorPosition(p);			
		}
	}
	
	public void update () { 
		if (entity.isAlive()) {
		    
		    if (canAttackPlayer())
		    	attackPlayer();
		    else if (canSeePlayer() && MathUtils.randomBoolean(0.7f))
		        hunt(entity.getSector().getPlayer());
		    else
		        wander();
		}
	}

	private boolean canSeePlayer(){
		return entity.canSee(entity.getSector().getPlayer());
	}
	
	private boolean canAttackPlayer() {
		if (canSeePlayer() && facingPlayer())
			return true;
		return false;
	}

	private boolean facingPlayer() {
		
		int x = entity.getSector().getPlayer().getSectorPosition().x() - entity.getSectorPosition().x();
		int y = entity.getSector().getPlayer().getSectorPosition().y() - entity.getSectorPosition().y();
		int direction = entity.getRotation();
		
		// if in the same xy plane and facing the right direction
		if (x == 0 && y > 0 && direction == Direction.NORTH)
			return true;
		
		if (x == 0 && y < 0 && direction == Direction.SOUTH)
			return true;
		
		if (y == 0 && x < 0 && direction == Direction.WEST)
			return true;
		
		if (y == 0 && x > 0 && direction == Direction.EAST)
			return true;
		
		return false;
	}
	
	private int playerDirection() {
		
		int x = entity.getSector().getPlayer().getSectorPosition().x() - entity.getSectorPosition().x();
		int y = entity.getSector().getPlayer().getSectorPosition().y() - entity.getSectorPosition().y();
		
		if (x == 0 && y > 0)
			return Direction.NORTH;
		
		if (x == 0 && y < 0)
			return Direction.SOUTH;
		
		if (y == 0 && x < 0)
			return Direction.WEST;
		
		if (y == 0 && x > 0)
			return Direction.EAST;
		
		return entity.getRotation();
	}
	
	private void attackPlayer() {

		if (Ship.class.isAssignableFrom(entity.getClass())){
			((Ship)entity).fireLaser();
		}
		
	}
	
	public void hunt(Entity target){
        
		entity.getSector().clearVisited();
		path = finder.findPath(entity,entity.getSectorPosition().x(), entity.getSectorPosition().y(), target.getSectorPosition().x(), target.getSectorPosition().y());
		
		
		//List<Point> points = new Path1(entity, target.getSectorPosition().x(), target.getSectorPosition().y()).points();
     
        if (path != null) {
	        int mx = path.getX(1) - entity.getSectorPosition().x();
	        int my = path.getY(1) - entity.getSectorPosition().y();
	     
	        if (entity.getSectorPosition().distanceTo(target.getSectorPosition()) > 4)
	        	entity.setSpeed(2);
	        else 
	        	entity.setSpeed(1);
	        
	        entity.moveDirection(Direction.getXYDirection(mx, my));
	        
			entity.setRotation(Direction.rotate90(entity.getRotation(), playerDirection()));
        }
    }
}
