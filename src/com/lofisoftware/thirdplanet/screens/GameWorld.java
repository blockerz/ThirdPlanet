package com.lofisoftware.thirdplanet.screens;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lofisoftware.thirdplanet.Assets;
import com.lofisoftware.thirdplanet.entities.Entity;
import com.lofisoftware.thirdplanet.universe.Universe;

public class GameWorld extends Actor {

	final private static int TILEWIDTH = 30;
	final private static int TILEHEIGHT = 20;
	//final private static int TILESIZE = 8;
	private static float tileWidthScale;
	private static float tileHeightScale;
	private static int offsetX, offsetY;
	private float hudX, hudY = 50;
	private static Universe universe;
	private boolean viewdebug = false;
	
    public GameWorld (Universe universe) {
    	GameWorld.universe = universe;
    	offsetX = offsetY = 0;
    }

    
    public void draw (SpriteBatch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);     
        
        this.clipBegin();
        drawActiveSector(batch);
        
        drawHUD(batch);
        this.clipEnd();
        
        super.draw(batch, parentAlpha);
    }
    
    private void drawHUD(SpriteBatch batch) {

    	Assets.getSmallFont().draw(batch, "Speed: " + drawEnergy(universe.getPlayer().getSpeed()), getHudX() + 10, getHudY() + 54);
    	Assets.getSmallFont().draw(batch, "Energy: " + drawEnergy(universe.getPlayer().getEnergy()), getHudX() + 10, getHudY() + 36);
    	Assets.getSmallFont().draw(batch, "Location: " + "  (" + universe.getPlayer().getSectorPosition().x() + "," + universe.getPlayer().getSectorPosition().y() + ")", getHudX() + 10, getHudY() + 18);		
		Assets.getSmallFont().draw(batch, "Hull strength: " + universe.getPlayer().getHitPoints() + "% ", getHudX() + 10, getHudY());
	}


	private String drawEnergy(int energy) {
		String result = "";
		for (int e = 0;e < energy; e++)
			result = result + "@ ";
			
		return result;
	}


	public void drawActiveSector(SpriteBatch batch) {
    	
    	tileWidthScale = getWidth()/TILEWIDTH;
    	tileHeightScale = getHeight()/TILEHEIGHT;
    	getOffsetX();
    	getOffsetY();
    	
    	for(int x = 0; x < TILEWIDTH; x++){
    		for(int y = 0; y < TILEHEIGHT; y++){
    			if (universe.getPlayer().canSee(x+offsetX, y+offsetY) || viewdebug){
    				
	    			batch.draw(universe.getActiveSector().getTexture(x+offsetX, y+offsetY), x*tileWidthScale+getOriginX(), y*tileHeightScale+getOriginY(), getOriginX(), getOriginY(),
	    					tileWidthScale, tileHeightScale, getScaleX(), getScaleY(), getRotation());
    			}
    		}
    	}
    	
    	List <Entity> entities = universe.getActiveSector().getEntities();
    	
    	for (Entity ent : entities) {
    		if (universe.getPlayer().canSee(ent) || viewdebug){
    			batch.draw(ent.getTextureRegion(), 
    					(ent.getSectorPosition().x()-offsetX)*tileWidthScale+getOriginX(), 
    					(ent.getSectorPosition().y()-offsetY)*tileHeightScale+getOriginY(), 
    					getOriginX(), 
    					getOriginY(),
    					tileWidthScale*ent.getWidth(), 
    					tileHeightScale*ent.getHeight(), 
    					getScaleX(), 
    					getScaleY(), 
    					getRotation());
    		}
		}
    	
    	/*
		for(int x = 0; x < TILEWIDTH; x++){
    		for(int y = 0; y < TILEHEIGHT; y++){
    			if (universe.getPlayer().canSee(x+offsetX, y+offsetY) || viewdebug){
	    			Entity entity = universe.getActiveSector().getEntity(new Point(x+offsetX,y+offsetY));
	    			
	    			if (entity != null){
	
	    				//
	    				//if (!entity.isTweening()) {
	    				//	entity.setSceenPosition(x*tileWidthScale, y*tileHeightScale);    
	        			//	
	    				//	if (entity.getLastScreenX() != entity.getScreenX() || entity.getLastScreenY() != entity.getScreenY()){
	        			//		entity.tweenMove(entity.getScreenX(), entity.getScreenY());
	        			//	}
	    				//}
						//
	   					//batch.draw(entity.getTextureRegion(), entity.getLastScreenX()+getOriginX(), entity.getLastScreenY()+getOriginY(), getOriginX(), getOriginY(),
	        			//		tileWidthScale, tileHeightScale, getScaleX(), getScaleY(), getRotation());
	   					//
	    				//batch.draw(TileFactory.getTileTexture(entity.getTile()), entity.getScreenX()+getOriginX(), entity.getScreenY()+getOriginY(), getOriginX(), getOriginY(),
	        			//		tileWidthScale, tileHeightScale, getScaleX(), getScaleY(), getRotation());
						
	   					// working
	    				batch.draw(entity.getTextureRegion(), x*tileWidthScale+getOriginX(), y*tileHeightScale+getOriginY(), getOriginX(), getOriginY(),
	        					tileWidthScale*entity.getWidth(), tileHeightScale*entity.getHeight(), getScaleX(), getScaleY(), getRotation());
	    				
	    			}
    			}
    		}   
    		
    	}
    	*/
    }
    
	public int getOffsetX() {
        offsetX = Math.max(0, Math.min(universe.getPlayer().getSectorPosition().x() - TILEWIDTH/2, universe.getActiveSector().getWidth() - TILEWIDTH));
        return offsetX; 
    }
    
    public int getOffsetY() {
        offsetY = Math.max(0, Math.min(universe.getPlayer().getSectorPosition().y() - TILEHEIGHT/2, universe.getActiveSector().getHeight() - TILEHEIGHT));
        return offsetY;
    }

	public float getHudX() {
		return getOriginX() + hudX;
	}


	public float getHudY() {
		return getOriginY() + hudY;
	}


	public static float getTileWidthScale() {
		return tileWidthScale;
	}

	public static float getTileHeightScale() {
		return tileHeightScale;
	}
}
