package com.lofisoftware.thirdplanet.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lofisoftware.thirdplanet.messages.EntityDamaged;
import com.lofisoftware.thirdplanet.messages.EntityDestroyed;
import com.lofisoftware.thirdplanet.messages.Message;
import com.lofisoftware.thirdplanet.messages.MessageBus;
import com.lofisoftware.thirdplanet.screens.TileFactory;
import com.lofisoftware.thirdplanet.universe.Direction;
import com.lofisoftware.thirdplanet.universe.Point;
import com.lofisoftware.thirdplanet.universe.Sector;
import com.lofisoftware.thirdplanet.universe.Tile;
import com.lofisoftware.thirdplanet.universe.UniverseFactory;
import com.lofisoftware.thirdplanet.util.Mover;

public class Entity implements Mover {
	private Point sectorPosition;
	private float lastScreenX, lastScreenY, screenX, screenY;
	private Tile tile;
	private Sector sector;
	private EntityAI entityAI;
	private int rotation;
	private int speed;
	private int maxSpeed;
	private TextureRegion textureRegion;
	private boolean updated = false;
	private String name;
	private boolean destructible;
	private int hitPoints;
	private int maxHitPoints;
	private boolean tweening = false;
	private int visionRadius;
	private int movesRemaining;
	private int attackPower;
	private int width;
	private int height;


	public Entity(Sector sector) {
		this.sector = sector;
		setRotation(0);
		setSpeed(0);
		lastScreenX = lastScreenY = screenX = screenY = -1;
		destructible = true;
		maxHitPoints = hitPoints = 1;
		visionRadius = 10;
		movesRemaining = 0;
		attackPower = 0;
		maxSpeed = 100;
		width = height = 1;
	}

	public Entity(Sector sector, Point p) {
		this(sector);
		setSectorPosition(p);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEntityAI(EntityAI entityAI) {
		this.entityAI = entityAI;
	}

	public EntityAI getEntityAI() {
		return entityAI;
	}

	public void moveBy(int mx, int my) {
		entityAI.onEnter(new Point(sectorPosition.x() + mx, sectorPosition.y()
				+ my), sector);
	}

	public void moveDirection(int direction) {

		if (isAlive()) {
			if (movesRemaining == 0)
				movesRemaining = getSpeed();
			
			if(movesRemaining > 0) {

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
				movesRemaining--;				
			}
		}
	}

	public Point getSectorPosition() {
		if (sectorPosition == null)
			return null;
		return sectorPosition.copy();
	}

	public void setSectorPosition(Point p) {
		setSectorPosition(p.x(), p.y());
	}

	public void setSectorPosition(int x, int y) {
		sectorPosition = new Point(x, y);
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = Math.min(maxSpeed, Math.max(speed, 0));
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getMaxHitPoints() {
		return maxHitPoints;
	}

	public void setMaxHitPoints(int maxHitPoints) {
		this.maxHitPoints = maxHitPoints;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
		textureRegion = null;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void update() {
		if(movesRemaining > 0)
			moveDirection(getRotation());
	}

	public int getMovesRemaining() {
		return movesRemaining;
	}

	public void setMovesRemaining(int movesRemaining) {
		this.movesRemaining = movesRemaining;
	}

	public boolean isAlive() {
		return hitPoints > 0;
	}

	public float getScreenX() {
		return screenX;
	}

	public float getScreenY() {
		return screenY;
	}

	public float getLastScreenX() {
		return lastScreenX;
	}

	public void setLastScreenX(float lastScreenX) {
		this.lastScreenX = lastScreenX;
	}

	public float getLastScreenY() {
		return lastScreenY;
	}

	public void setLastScreenY(float lastScreenY) {
		this.lastScreenY = lastScreenY;
	}

	public void setSceenPosition(float x, float y) {
		if (lastScreenX == -1 && lastScreenY == -1){
			lastScreenX = x; 
			lastScreenY = y;
		}
		else {
			lastScreenX = screenX; 
			lastScreenY = screenY;
		}
		screenX = x;
		screenY = y;
	}

	public TextureRegion getTextureRegion() {
		if (textureRegion == null)
			textureRegion = TileFactory.getTileTexture(getTile());
		return textureRegion;
	}

	public Sector getSector() {
		return sector;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public boolean isDestructible() {
		return destructible;
	}

	public void setDestructible(boolean destructible) {
		this.destructible = destructible;
	}

	public int getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(int hitPoints) {
		if (isDestructible())
			this.hitPoints = Math.min(hitPoints, maxHitPoints);
	}

	public void modifyHitPoints(Entity other, int amount) {
		
		if (other != null && other.isDestructible()) {
			other.setHitPoints(other.hitPoints += amount);
			
			if (!other.isAlive())
				destroy(other);
			else
				MessageBus.publish(new EntityDamaged(sector, this, other, Message.wereWas(other.getName())
						+ "damaged by " + this.getName() + "."));
		}
	}

	public void destroy(Entity other) {
		
		if (other == this) {
			MessageBus.publish(new EntityDestroyed(sector, this, this, Message.wereWas(getName())
				+ "destroyed."));			
			sector.remove(other);
			leaveExplosion(other.getSectorPosition());
		}
		// otherwise destroy other
		else {
			MessageBus.publish(new EntityDestroyed(sector, this, other, Message.wereWas(other.getName())
					+ "destroyed by " + this.getName() + "."));			
			sector.remove(other);
			leaveExplosion(other.getSectorPosition());
		}			
	}

	public void leaveExplosion(Point p) {
		UniverseFactory.createExplosion(getSector(), p);
	}

	public int getVisionRadius() {
		return visionRadius;
	}

	public void setVisionRadius(int visionRadius) {
		this.visionRadius = visionRadius;
	}

	public boolean canSee(int wx, int wy){
        return entityAI.canSee(wx, wy);
    }
	
	public boolean canSee(Entity entity){
		boolean result = false;
		for (int x = entity.getSectorPosition().x(); x < entity.getSectorPosition().x() + entity.getWidth(); x++){
			for (int y = entity.getSectorPosition().y(); y < entity.getSectorPosition().y() + entity.getHeight();y++){
				if (this.canSee(x, y))
						result = true;
			}
		}
		return result;
	}
	
	
	public int getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

	public boolean isTweening() {
		return tweening;
	}

	public void setTweening(boolean tweening) {
		this.tweening = tweening;
	}
	
	/*
	public void tweenMove(float targetX, float targetY) {
		// kill current tween - or pre-existing
		//ThirdPlanet.getTweenManager().killTarget(this);
		EntityTweenCallback cb = new EntityTweenCallback(this);
		setTweening(true);
		// move
		Tween.to(this, EntityAccessor.POS_XY, 0.5f).target(targetX, targetY)
				.ease(TweenEquations.easeNone)
				.setCallback((TweenCallback)cb).setCallbackTriggers(TweenCallback.COMPLETE)
				.start(ThirdPlanet.getTweenManager());
	}
	*/

	public boolean canEnter(int x, int y) {
		Point p = new Point(x,y);
		Entity e = sector.getEntity(p);
		if (sector.isValidPos(p) && !sector.getTile(p).isSolid() && (e==null || e == this || e == sector.getPlayer()))
			return true;
		return false;
	}
}
