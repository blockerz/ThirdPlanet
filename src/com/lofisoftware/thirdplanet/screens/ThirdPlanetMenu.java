package com.lofisoftware.thirdplanet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.lofisoftware.thirdplanet.Assets;
import com.lofisoftware.thirdplanet.ThirdPlanet;

public class ThirdPlanetMenu implements Screen {

	private int screenWidth;
	private int screenHeight;
	
	private Stage stage;
	private Table root;
	TextButton newGameButton;
	TextButton resumeGameButton;
	TextButton helpButton;
	TextButton creditsButton;
	TextButtonStyle buttonStyle;
	Label titleLabel;
	Table menu;	
	
	public ThirdPlanetMenu(){
		
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
		
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = Assets.getGameskin().getDrawable("green-border");
		buttonStyle.down = Assets.getGameskin().getDrawable("green-border-gray");
		buttonStyle.font = Assets.getSmallFont();
		
		newGameButton = new TextButton("New Game", buttonStyle);
		newGameButton.setWidth(screenWidth/6);
		newGameButton.setHeight(screenHeight/10);
		//button.setX(Gdx.graphics.getWidth() / 2- button.getWidth() / 2);
		//button.setY(Gdx.graphics.getHeight() / 2- button.getHeight() / 2);

		resumeGameButton = new TextButton("Resume Game", buttonStyle);
		resumeGameButton.setWidth(screenWidth/6);
		resumeGameButton.setHeight(screenHeight/10);

		helpButton = new TextButton("Help", buttonStyle);
		helpButton.setWidth(screenWidth/6);
		helpButton.setHeight(screenHeight/10);
		
		creditsButton = new TextButton("Credits", buttonStyle);
		creditsButton.setWidth(screenWidth/6);
		creditsButton.setHeight(screenHeight/10);
		
		LabelStyle labelStyle = new LabelStyle(Assets.getLargeFont(), Color.WHITE);
		titleLabel = new Label("Third Planet", labelStyle);
		//label.setX(0);
		//label.setY(Gdx.graphics.getHeight() / 2 + 100);
		titleLabel.setWidth(screenWidth/4);
		titleLabel.setAlignment(Align.center);
		
		menu = new Table();
		menu.setSize(screenWidth*.75f, screenHeight*.75f);
		menu.setBackground(Assets.getGameskin().getDrawable("green-border"));
		//gameworld.setOrigin(0, screenHeight*.25f);
		//gameworld.setBounds(0f, screenHeight*.25f, screenWidth*.75f, screenHeight*.75f);
		
		root.add(menu).width(screenWidth*.75f).height(screenHeight*.75f);
		
		root.addListener(new InputListener() {
			
			public boolean keyDown(InputEvent event, int keycode) {
				
				switch (keycode) {		
				case Input.Keys.ENTER:
					ThirdPlanet.changeGameState(ThirdPlanet.GAME_INTRO);
					return true;
				}
				return super.keyDown(event, keycode);
			}
						
		});
		
		newGameButton.addListener(new InputListener() {
			
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				//Gdx.app.log(ThirdPlanet.LOG, "Button TouchDown Event");
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				//Gdx.app.log(ThirdPlanet.LOG, "Button TouchUp Event");
				ThirdPlanet.changeGameState(ThirdPlanet.GAME_INTRO);
			}
		});
		
		resumeGameButton.addListener(new InputListener() {
			
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				//Gdx.app.log(ThirdPlanet.LOG, "Button TouchDown Event");
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				//Gdx.app.log(ThirdPlanet.LOG, "Button TouchUp Event");
				ThirdPlanet.changeGameState(ThirdPlanet.GAME_RESUME);
			}
		});
		
		helpButton.addListener(new InputListener() {
			
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				//Gdx.app.log(ThirdPlanet.LOG, "Button TouchDown Event");
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				//Gdx.app.log(ThirdPlanet.LOG, "Button TouchUp Event");
				ThirdPlanet.changeGameState(ThirdPlanet.GAME_HELP);
			}
		});
		
		creditsButton.addListener(new InputListener() {
			
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				//Gdx.app.log(ThirdPlanet.LOG, "Button TouchDown Event");
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				//Gdx.app.log(ThirdPlanet.LOG, "Button TouchUp Event");
				ThirdPlanet.changeGameState(ThirdPlanet.GAME_CREDITS);
			}
		});
		
		menu.add(titleLabel).width(screenWidth/4).height(screenHeight/10);
		menu.row();
		menu.row();
		menu.add(newGameButton).width(screenWidth/6).height(screenHeight/10);
		menu.row();
		menu.row();
		menu.add(resumeGameButton).width(screenWidth/6).height(screenHeight/10);
		menu.row();
		menu.row();
		menu.add(helpButton).width(screenWidth/6).height(screenHeight/10);
		menu.row();
		menu.row();
		menu.add(creditsButton).width(screenWidth/6).height(screenHeight/10);
		
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
