package com.lofisoftware.thirdplanet.universe;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.lofisoftware.thirdplanet.entities.BaseShip;
import com.lofisoftware.thirdplanet.entities.Entity;
import com.lofisoftware.thirdplanet.entities.LargeEntity;
import com.lofisoftware.thirdplanet.entities.Player;
import com.lofisoftware.thirdplanet.entities.SpaceStation;
import com.lofisoftware.thirdplanet.screens.TileFactory;
import com.lofisoftware.thirdplanet.util.Mover;
import com.lofisoftware.thirdplanet.util.TileBasedMap;

public class Sector implements TileBasedMap {
	
	public static int SECTOR_TILE_SIZE = 100;
	
	private Player player;
	private BaseShip baseShip;
	private BaseShip motherShip;
	private SpaceStation beacon;
	private Tile [][] map;
	private TextureRegion [][] textures;
	private int width, height;
	private List<Entity> entities;
	private boolean[][] visited;

    public Sector(Tile[][] map){
        this.map = map;
        this.setWidth(map.length);
        this.setHeight(map[0].length);
        this.textures = new TextureRegion [getWidth()][getHeight()];
        this.entities = new ArrayList<Entity>();
        visited = new boolean[getWidth()][getHeight()];
    }
    
    public Tile getTile(Point p){
        return getTile(p.x(),p.y());
    }
    
    public Tile getTile(int x, int y){
        if (isValidPos(x,y))
            return map[x][y];
        else
            return Tile.EMPTY;
    }
    
    public boolean setTile(Point p, Tile tile){
    	if (isValidPos(p) && tile != null){
    		map[p.x()][p.y()] = tile;
            return true;
    	}
    	return false;
    }
        
    public List<Entity> getEntities() {
		return entities;
	}

	public TextureRegion getTexture(int x, int y){
        if (isValidPos(x,y))
            return textures[x][y];
        else
            return TileFactory.createEmptyTile();
    }

    public boolean setTexture(int x, int y, TextureRegion texture){
    	if (isValidPos(x,y) && texture != null){
    		textures[x][y] = texture;
            return true;
    	}
    	return false;
    }
    
    public boolean isValidPos(Point p){
    	return isValidPos(p.x(),p.y());
    }
    
    public boolean isValidPos(int x, int y){
    	if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight())
    		return false;
    	return true;
    }
    
	public boolean canEnter(Point p){
		if (isValidPos(p) && !getTile(p).isSolid() && (getEntity(p)==null || !getEntity(p).getTile().isSolid()))
			return true;
		return false;
	}
	
	public Entity getEntity(Point p) {
		for (Entity a : entities) {
    		if (a.getSectorPosition().x() == p.x() && a.getSectorPosition().y() == p.y())
    			return a;
    		else if (LargeEntity.class.isAssignableFrom(a.getClass())) {
    			if (((LargeEntity)a).containsPoint(p))
    			return a;
    		}
		}
		return null;
	}
	

	public void addPlayer(Player entity, Point p) {

		int attempts = 0;
		int range = 3;
		
		while (entity.getSectorPosition() == null){
			if (canEnter(p) && getEntity(p) == null)
				entity.setSectorPosition(p);
			else {
				p = new Point ((int)MathUtils.random(p.x()-5,p.x()+range), 
	        		(int)MathUtils.random(p.y()-5,p.y()+range));
				attempts++;
				
				if (attempts >= (range *range))
					range++;
			}
		}
		player = entity;
		entities.add(entity);
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void add(Entity entity) {
		add(entity, SECTOR_TILE_SIZE, SECTOR_TILE_SIZE);
	}
	
	public void add(Entity entity, int x, int y) {
		Point p;
		int startX = MathUtils.random(0, SECTOR_TILE_SIZE - x);
		int startY = MathUtils.random(0, SECTOR_TILE_SIZE - y);
		
		while (entity.getSectorPosition() == null){
			p = new Point ((int)MathUtils.random(startX, startX+x-1), 
	        		(int)MathUtils.random(startY, startY+y-1));

			if (canEnter(p) && getEntity(p) == null)
				entity.setSectorPosition(p);
		}
		entities.add(entity);
	}
	

	public void add(Entity entity, Point p) {
		if (entity != null && p != null){
			entity.setSectorPosition(p);
			entities.add(entity);
		}
			
	}
	
	public void remove(Entity entity) {
		
		if (entities.contains(entity)) {
			entity.setHitPoints(0);
			entities.remove(entity);
		}
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
	
	public Color getColor(Point p){
        if (isValidPos(p))
            return getTile(p).getColor();
        
        return Tile.EMPTY.getColor();
    }

	public void update() {
		List<Entity> toUpdate = new ArrayList<Entity>();
		
        if(getPlayer().isUpdated()){
        	toUpdate.addAll(entities);
        	getPlayer().addTurn();
        	getPlayer().setUpdated(false);
        }
        else {
        	for (Entity e : entities)
        		if (e.getMovesRemaining() > 0)
        			toUpdate.add(e);
        }
        	
    	for (Entity a : toUpdate)
    			a.update();
    	
    	List<Entity> stillAlive = new ArrayList<Entity>();
    	
    	for (Entity a : entities)
    		if (a.isAlive())
    			stillAlive.add(a);
    	
    	entities = stillAlive;	
	}

	@Override
	public int getWidthInTiles() {
		return this.width;
	}

	@Override
	public int getHeightInTiles() {
		return this.height;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		visited[x][y] = true;		
	}
	
	public boolean visited(int x, int y) {
		return visited[x][y];
	}
	
	public void clearVisited() {
		for (int x=0;x<getWidthInTiles();x++) {
			for (int y=0;y<getHeightInTiles();y++) {
				visited[x][y] = false;
			}
		}
	}

	@Override
	public boolean blocked(Mover mover, int x, int y) {		
		return !mover.canEnter(x,y);
	}

	@Override
	public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
		return 1;
	}

	public BaseShip getMotherShip() {
		return motherShip;
	}

	public void setMotherShip(BaseShip motherShip) {
		this.motherShip = motherShip;
	}

	public BaseShip getBaseShip() {
		return baseShip;
	}

	public void setBaseShip(BaseShip baseShip) {
		this.baseShip = baseShip;
	}

	public SpaceStation getBeacon() {
		return beacon;
	}

	public void setBeacon(SpaceStation beacon) {
		this.beacon = beacon;
	}

}
