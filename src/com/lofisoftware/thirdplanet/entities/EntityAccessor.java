package com.lofisoftware.thirdplanet.entities;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.Color;

public class EntityAccessor implements TweenAccessor<Entity> {
	public static final int POS_XY = 1;          
	public static final int COLOR = 2;     

	@Override     
	public int getValues(Entity target, int tweenType, float[] returnValues) {         
		switch (tweenType) {             
		case POS_XY:                 
			returnValues[0] = target.getLastScreenX();                 
			returnValues[1] = target.getLastScreenY();                 
			return 2;                          
		case COLOR:                 
				returnValues[0] = target.getTile().getColor().r;                 
				returnValues[1] = target.getTile().getColor().g;                 
				returnValues[2] = target.getTile().getColor().b;                 
				returnValues[3] = target.getTile().getColor().a;                 
				return 4;             
		default: 
				assert false; 
				return -1;         
		}     
	}   
	
	@Override     
	public void setValues(Entity target, int tweenType, float[] newValues) {         
		switch (tweenType) {             
		case POS_XY: 
			target.setLastScreenX(newValues[0]);
			target.setLastScreenY(newValues[1]); 
			break;                        
		case COLOR:                 
			Color c = target.getTile().getColor();                 
			c.set(newValues[0], newValues[1], newValues[2], newValues[3]);               
			target.getTile().setColor(c);                 
			break;             
		default: 
			assert false;         
		}     
	} 
}
