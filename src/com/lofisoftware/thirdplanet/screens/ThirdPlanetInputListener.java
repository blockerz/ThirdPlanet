package com.lofisoftware.thirdplanet.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.lofisoftware.thirdplanet.ThirdPlanet;
import com.lofisoftware.thirdplanet.universe.Direction;
import com.lofisoftware.thirdplanet.universe.Universe;

public class ThirdPlanetInputListener extends InputListener {

	private Universe universe;
	
	public ThirdPlanetInputListener(Universe universe) {
		super();
		this.universe = universe;
		
	}

	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer,
			int button) {

		return super.touchDown(event, x, y, pointer, button);
	}

	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer,
			int button) {

		super.touchUp(event, x, y, pointer, button);
	}

	@Override
	public boolean keyDown(InputEvent event, int keycode) {

		switch (keycode) {
		case Input.Keys.NUMPAD_8:
		case Input.Keys.K:
		case Input.Keys.W:
		case Input.Keys.UP:
			universe.getPlayer().moveDirection(Direction.NORTH);
			return true;
		case Input.Keys.NUMPAD_4:
		case Input.Keys.H:
		case Input.Keys.A:
		case Input.Keys.LEFT:
			universe.getPlayer().moveDirection(Direction.WEST);
			return true;
		case Input.Keys.NUMPAD_2:
		case Input.Keys.J:
		case Input.Keys.S:
		case Input.Keys.DOWN:
			universe.getPlayer().moveDirection(Direction.SOUTH);
			return true;
		case Input.Keys.NUMPAD_6:
		case Input.Keys.L:
		case Input.Keys.D:
		case Input.Keys.RIGHT:
			universe.getPlayer().moveDirection(Direction.EAST);
			return true;
		case Input.Keys.NUMPAD_9:
		case Input.Keys.PERIOD:
		case Input.Keys.E:
			universe.getPlayer().speedUp();
			return true;
		case Input.Keys.NUMPAD_7:
		case Input.Keys.COMMA:
		case Input.Keys.Q:
			universe.getPlayer().slowDown();
			return true;
		case Input.Keys.PLUS:
		case Input.Keys.SPACE:
			universe.getPlayer().fireLaser();
			return true;
		//case Input.Keys.P:
		//	universe.getPlayer().setDestructible(false);
		//	return true;		
		case Input.Keys.ESCAPE:
			ThirdPlanet.changeGameState(ThirdPlanet.GAME_MENU);
			return true;
		}
		
		return super.keyDown(event, keycode);
	}

}
