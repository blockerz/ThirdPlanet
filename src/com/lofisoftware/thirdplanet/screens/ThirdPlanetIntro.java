package com.lofisoftware.thirdplanet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.lofisoftware.thirdplanet.Assets;
import com.lofisoftware.thirdplanet.ThirdPlanet;

public class ThirdPlanetIntro implements Screen {

	private int screenWidth;
	private int screenHeight;
	
	private Stage stage;
	private Table root;
	Label titleLabel;
	Label blankLabel;
	Label line1;
	Label line2;
	Label line3;
	Table menu;
	
	
	public ThirdPlanetIntro(){
		
		screenWidth = ThirdPlanet.GAME_WIDTH;
		screenHeight = ThirdPlanet.GAME_HEIGHT;
		
		stage = new Stage(0, 0, false);
		
		Gdx.input.setInputProcessor(stage);

		root = new Table();
		root.setSize(screenWidth, screenHeight);
		root.setBounds(0, 0, screenWidth, screenHeight);
		root.setBackground(Assets.getGameskin().getDrawable("black-background"));
		root.align(Align.center);
		//root.addListener(new ThirdPlanetInputListener(universe));
		root.setTouchable(Touchable.enabled);
		stage.setKeyboardFocus(root);
		stage.addActor(root);
		
		//root.debug().defaults().space(6);
		
		LabelStyle labelStyle = new LabelStyle(Assets.getLargeFont(), Color.WHITE);
		
		titleLabel = new Label("Introduction", labelStyle);
		//label.setX(0);
		//label.setY(Gdx.graphics.getHeight() / 2 + 100);
		titleLabel.setWidth(screenWidth/3);
		titleLabel.setAlignment(Align.center);
		
		labelStyle = new LabelStyle(Assets.getSmallFont(), Color.WHITE);
		
		blankLabel = new Label(" ", labelStyle);
		
		line1 = new Label("Captain, you are our last hope of reaching the Third Planet. We need you now more than ever. Our other shuttles have been lost. If you don't come through, we will never make it home. It is a heavy burden but I know you can carry it.", labelStyle);
		line1.setWrap(true);
		line2 = new Label(" ", labelStyle);
		line2.setWrap(true);
		line3 = new Label("Find the final clues left by the pioneers. Each beacon we find leads us a step closer to home. You must avoid the aliens and whatever else is out there. You must survive at all costs.", labelStyle);
		line3.setWrap(true);
		
		menu = new Table();
		menu.setBackground(Assets.getGameskin().getDrawable("green-border"));
		menu.setSize(screenWidth*.75f, screenHeight*.75f);
		//gameworld.setOrigin(0, screenHeight*.25f);
		//gameworld.setBounds(0f, screenHeight*.25f, screenWidth*.75f, screenHeight*.75f);
		
		root.add(menu).width(screenWidth*.75f).height(screenHeight*.75f);
		
		root.addListener(new InputListener() {
			
			
			public boolean keyDown(InputEvent event, int keycode) {
				ThirdPlanet.changeGameState(ThirdPlanet.GAME_PLAY);
				return true;
								
			}
			
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				//Gdx.app.log(ThirdPlanet.LOG, "Button TouchDown Event");
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				//Gdx.app.log(ThirdPlanet.LOG, "Button TouchUp Event");
				ThirdPlanet.changeGameState(ThirdPlanet.GAME_PLAY);
			}
		});
		
		menu.add(titleLabel).width(screenWidth/2).height(screenHeight/10);
		menu.row();
		menu.add(blankLabel);
		menu.row();
		menu.add(line1).width(screenWidth/2).height(screenHeight/10);
		menu.row();
		menu.row();
		menu.add(line2).width(screenWidth/2).height(screenHeight/10);
		menu.row();
		menu.row();
		menu.add(line3).width(screenWidth/2).height(screenHeight/10);
		
	}

	
	//private SpriteBatch batch;
    //private Skin skin;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());	
		stage.draw();
		Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		Vector2 size = Scaling.fit.apply(screenWidth, screenHeight, width, height);
		int viewportX = (int)(width - size.x) / 2;
		int viewportY = (int)(height - size.y) / 2;
		int viewportWidth = (int)size.x;
		int viewportHeight = (int)size.y;
		Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
		stage.setViewport(screenWidth, screenHeight, true, viewportX, viewportY, viewportWidth, viewportHeight);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}

}


