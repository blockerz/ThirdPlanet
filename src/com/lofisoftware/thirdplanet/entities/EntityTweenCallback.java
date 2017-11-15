package com.lofisoftware.thirdplanet.entities;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

public class EntityTweenCallback implements TweenCallback {
	private Entity e;
	public EntityTweenCallback (Entity e){
		super();
		this.e = e;
	}
	
	@Override
	public void onEvent(int type, BaseTween<?> source) {
		e.setTweening(false);				
	}
}
