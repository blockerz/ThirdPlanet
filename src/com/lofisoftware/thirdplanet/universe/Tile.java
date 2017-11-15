package com.lofisoftware.thirdplanet.universe;

import com.badlogic.gdx.graphics.Color;
import com.lofisoftware.thirdplanet.Assets;

public enum Tile {
	// Tile name, Tile textureName, Tile Solid
    BOUNDS("Bounds", Color.BLACK, true,0,0,0),
    EMPTY("Empty", Color.MAGENTA, false,0,0,0),
    PLAYER_SHIP_NORTH("Player", Color.GREEN, true,Assets.SHIPS8,0,8),
    PLAYER_SHIP_EAST("Player", Color.GREEN, true,Assets.SHIPS8,0,9),
    PLAYER_SHIP_SOUTH("Player", Color.GREEN, true, Assets.SHIPS8,0,10),
    PLAYER_SHIP_WEST("Player", Color.GREEN, true, Assets.SHIPS8,0,11),
    SPACE("Space", Color.BLACK, false,0,0,0),
    NEBULA("Nebula", Color.BLACK, false,0,0,0),
    EXPLODE("Space", Color.BLACK, false,0,0,0),
    LASER_NORTH_SOUTH("Laser", Color.RED, false,Assets.ITEMS8,2,7),
    LASER_EAST_WEST("Laser", Color.RED, false,Assets.ITEMS8,3,6),
    ASTEROID8("Asteroid", Color.BLACK, true,Assets.ASTEROIDS8,0,0),
    ASTEROID16("Asteroid", Color.BLACK, true,Assets.ASTEROIDS16,0,0),
    COMET24_NORTH("Comet", Color.ORANGE, true,Assets.COMETS24,0,1),
    COMET24_EAST("Comet", Color.ORANGE, true,Assets.COMETS24,0,0),
    COMET24_SOUTH("Comet", Color.ORANGE, true,Assets.COMETS24,0,3),
    COMET24_WEST("Comet", Color.ORANGE, true,Assets.COMETS24,0,2),
    EXPLOSION("Explosion", Color.RED, false,Assets.ITEMS8,4,22),
    SPACESTATION("Beacon", Color.BLUE, true,Assets.SPACESTATION,0,0),
    BASESHIP("Base Ship", Color.GRAY, true,Assets.BASESHIP,0,0),
    THIRDPLANET("Third Planet", Color.BLUE, false,Assets.THIRDPLANET,0,0),
    ALIENBASESHIP("Mother Ship", Color.YELLOW, true,Assets.ALIENBASESHIP,0,0),
    ALIENBASESHIPD("Mother Ship", Color.YELLOW, true,Assets.ALIENBASESHIPD,0,0),
    ALIEN_SHIP1_NORTH("Alien Ship", Color.ORANGE, true,Assets.ALIENSHIPS8,0,0),
    ALIEN_SHIP1_EAST("Alien Ship", Color.ORANGE, true,Assets.ALIENSHIPS8,0,1),
    ALIEN_SHIP1_SOUTH("Alien Ship", Color.ORANGE, true,Assets.ALIENSHIPS8,0,2),
    ALIEN_SHIP1_WEST("Alien Ship", Color.ORANGE, true,Assets.ALIENSHIPS8,0,3);
 

    private String name;
	private Color color;
    private boolean solid;
    
    //texture region
    private int region;
	private int regionX;
    private int regionY;
    
	Tile(String name, Color color, boolean solid, int region, int regionX, int regionY){
        this.name = name;
        this.setColor(color);
        this.setSolid(solid);
        this.setRegion(region, regionX, regionY);
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	   
	public void setRegion(int region, int regionX, int regionY) {
		this.region = region; 
		this.regionX = regionX;
		this.regionY = regionY;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}
    public int getRegion() {
		return region;
	}

	public int getRegionX() {
		return regionX;
	}

	public int getRegionY() {
		return regionY;
	}

}
