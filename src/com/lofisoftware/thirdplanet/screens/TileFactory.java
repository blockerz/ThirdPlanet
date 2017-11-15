package com.lofisoftware.thirdplanet.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.lofisoftware.thirdplanet.Assets;
import com.lofisoftware.thirdplanet.universe.Tile;

public class TileFactory {

	public static int TILE_SIZE = 8;
    private static int STAR_DENSITY = 3;
    
    public static TextureRegion getTileTexture(Tile tile) {
    	TextureRegion t;
    	
    	switch(tile) {
    	case SPACE:
    		t = createStarTile(STAR_DENSITY, Color.BLACK);
    		break;
    	case NEBULA:
    		Color nebulaColor = new Color(169f,169f,169f,0.7f);
    		nebulaColor.a = MathUtils.random(0.2f, 0.5f);
    		t = createStarTile(STAR_DENSITY, nebulaColor);
    		break;
    	case ASTEROID8:
    		t = Assets.getTexture(tile.getRegion(), tile.getRegionX(), MathUtils.random(7));
    		break;
    	case ASTEROID16:
    		t = Assets.getTexture(tile.getRegion(), tile.getRegionX(), MathUtils.random(3));
    		break;
    	case EXPLODE:
    		Color explosionColor = Color.RED;
    		explosionColor.a = MathUtils.random(0.2f, 0.5f);
    		t = createStarTile(STAR_DENSITY, explosionColor);
    		break;
    	default:
    		t = Assets.getTexture(tile.getRegion(), tile.getRegionX(), tile.getRegionY());
    	}
    	
    	return t;
    }
    
    public static TextureRegion createStarTile(int density, Color color) {
    	return createTextureRegionFromPixmap(TileFactory.createStarPixmap(MathUtils.random(density), color));
    }
    
	public static Pixmap createStarPixmap(int density, Color color) {
		Pixmap stars = new Pixmap(TileFactory.TILE_SIZE,TileFactory.TILE_SIZE,Pixmap.Format.RGBA4444); 
		
		stars.setColor(Color.rgba8888(color));
		stars.fill();
		for (int s = 0; s < density; s++) {
			stars.drawPixel(MathUtils.random(0, TileFactory.TILE_SIZE-1), MathUtils.random(0, TileFactory.TILE_SIZE-1), Color.rgba8888(Color.WHITE));
		}

		return stars;
	}

	public static Pixmap createEmptyPixmap() {
		Pixmap empty = new Pixmap(TileFactory.TILE_SIZE,TileFactory.TILE_SIZE,Pixmap.Format.RGBA4444); 
		
		empty.setColor(Color.rgba4444(Color.MAGENTA));
		empty.fill();
		return empty;
	}
	
	public static TextureRegion createTextureRegionFromPixmap(Pixmap pixmap) {
		Texture texture = new Texture(pixmap);
		TextureRegion region = new TextureRegion(texture);
		return region;
	}

	public static TextureRegion createEmptyTile() {
		return createTextureRegionFromPixmap(TileFactory.createEmptyPixmap());
	}
}
