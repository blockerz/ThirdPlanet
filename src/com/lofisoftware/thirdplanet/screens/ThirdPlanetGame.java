package com.lofisoftware.thirdplanet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.lofisoftware.thirdplanet.Assets;
import com.lofisoftware.thirdplanet.ThirdPlanet;
import com.lofisoftware.thirdplanet.messages.Handler;
import com.lofisoftware.thirdplanet.messages.Message;
import com.lofisoftware.thirdplanet.messages.MessageBus;
import com.lofisoftware.thirdplanet.messages.Note;
import com.lofisoftware.thirdplanet.universe.ThirdPlanetDirector;

public class ThirdPlanetGame implements Screen, Handler{

	private int screenWidth;
	private int screenHeight;
	
	private Stage stage;
	private Table root;
	private GameWorld gameworld;
	private ThirdPlanetDirector director;
	private MessageLog messageLog;
	
	public ThirdPlanetGame(){
		
		screenWidth = ThirdPlanet.GAME_WIDTH;
		screenHeight = ThirdPlanet.GAME_HEIGHT;
		
		MessageBus.subscribe(this);
		
		director = new ThirdPlanetDirector();
		
		stage = new Stage(0, 0, false);
		
		Gdx.input.setInputProcessor(stage);

		root = new Table();
		root.setSize(screenWidth, screenHeight);
		root.setBounds(0, 0, screenWidth, screenHeight);
		root.setBackground(Assets.getGameskin().getDrawable("green-border"));
		root.align(Align.left + Align.top);
		root.addListener(new ThirdPlanetInputListener(director.getUniverse()));
		root.setTouchable(Touchable.enabled);
		stage.setKeyboardFocus(root);
		stage.addActor(root);
		
		//root.debug().defaults().space(6);
		
		gameworld = new GameWorld(director.getUniverse());
		gameworld.setSize(screenWidth*.80f, screenHeight*.80f);
		gameworld.setOrigin(0, screenHeight*.20f);
		gameworld.setBounds(0f, screenHeight*.20f, screenWidth*.80f, screenHeight*.80f);
		
		root.add(gameworld).width(screenWidth*.80f).height(screenHeight*.80f);
		
		messageLog = new MessageLog();
		float offset = 10;
		messageLog.setSize(screenWidth*.80f-offset, screenHeight*.20f-offset);
		messageLog.setOrigin(offset, offset);
		messageLog.setBounds(offset, offset, screenWidth*.80f-offset, screenHeight*.20f-offset);
		messageLog.getScroller().setBounds(offset, offset, screenWidth*.80f-5, screenHeight*.20f-offset);
		root.row();
		root.add(messageLog.getScroller()).width(screenWidth*.80f-offset).height(screenHeight*.20f-offset);
		//root.add(messageLog);
		
	}
	

	@Override
	public void handle(Message message) {
		
		if (Note.class.isAssignableFrom(message.getClass()))
			messageLog.record(message);
		else if (message.involves(director.getUniverse().getPlayer())){
			messageLog.record(message);
		}
		
	}
	
	//private SpriteBatch batch;
    //private Skin skin;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		director.update();	
		stage.draw();
		
		if (ThirdPlanetDirector.getGameState() == ThirdPlanetDirector.GAME_STATE.RUNNING)
			messageLog.updateMessages();
		//Table.drawDebug(stage);
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
