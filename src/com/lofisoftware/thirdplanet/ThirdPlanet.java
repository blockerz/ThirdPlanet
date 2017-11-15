package com.lofisoftware.thirdplanet;

import com.badlogic.gdx.Game;
import com.lofisoftware.thirdplanet.screens.ThirdPlanetCredits;
import com.lofisoftware.thirdplanet.screens.ThirdPlanetGame;
import com.lofisoftware.thirdplanet.screens.ThirdPlanetHelp;
import com.lofisoftware.thirdplanet.screens.ThirdPlanetIntro;
import com.lofisoftware.thirdplanet.screens.ThirdPlanetLoose;
import com.lofisoftware.thirdplanet.screens.ThirdPlanetMenu;
import com.lofisoftware.thirdplanet.screens.ThirdPlanetWin;

public class ThirdPlanet extends Game {
	//private FPSLogger fps;
	//private static TweenManager tweenManager;

	public static final String NAME = "Third Planet";
	public static final String VERSION = "0.7 Beta";
	public static final String LOG = "Third Planet";
	public final static int GAME_WIDTH = 800;
	public final static int GAME_HEIGHT = 600;
	
	public static final int GAME_MENU = 1;
	public static final int GAME_PLAY = 2;
	public static final int GAME_LOST = 3;
	public static final int GAME_WON = 4;
	public static final int GAME_CREDITS = 5;
	public static final int GAME_HELP = 6;
	public static final int GAME_RESUME = 7;
	public static final int GAME_INTRO = 8;

	private static ThirdPlanetMenu game_menu;
	private static ThirdPlanetGame game_play;	
	private static ThirdPlanetCredits game_credits;
	private static ThirdPlanetWin game_won;
	private static ThirdPlanetLoose game_lost;
	private static ThirdPlanetHelp game_help;
	private static ThirdPlanetIntro game_intro;
	
	private static int game_state;
	private static int new_game_state;

	//public static TweenManager getTweenManager() {
	//	return tweenManager;
	//}

	//public static void setTweenManager(TweenManager tweenManager) {
	//	ThirdPlanet.tweenManager = tweenManager;
	//}

	@Override
	public void create() {

		Assets.load();

		//fps = new FPSLogger();

		//setTweenManager(new TweenManager());

		// default is 3, yet for rgba color setting we need to raise to 4
		//Tween.setCombinedAttributesLimit(4);
		//Tween.registerAccessor(Entity.class, new EntityAccessor());
		// Tween.registerAccessor(OrthographicCamera.class, new CameraAccessor());
		
		new_game_state = GAME_MENU;
		game_state = -1;
				
	}
	
	public static void changeGameState(int state) {
		new_game_state = state;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render() {		
		
		if (game_state != new_game_state) {
			game_state = new_game_state;
			
			switch (game_state) {
			
			case GAME_MENU:
				if (game_menu == null)
					game_menu = new ThirdPlanetMenu();

				setScreen(game_menu);
				break;
			case GAME_PLAY:
				if (game_play != null)
					game_play.dispose();
				game_play = new ThirdPlanetGame();

				setScreen(game_play);
				break;
			case GAME_INTRO:
				game_intro = new ThirdPlanetIntro();

				setScreen(game_intro);
				break;
			case GAME_RESUME:
				if (game_play == null)
					game_play = new ThirdPlanetGame();

				setScreen(game_play);
				break;
			case GAME_HELP:
				if (game_help == null)
					game_help = new ThirdPlanetHelp();

				setScreen(game_help);
				break;
			case GAME_CREDITS:
				if (game_credits == null)
					game_credits = new ThirdPlanetCredits();

				setScreen(game_credits);
				break;
			case GAME_LOST:
				if (game_lost == null)
					game_lost = new ThirdPlanetLoose();

				setScreen(game_lost);
				break;
			case GAME_WON:
				if (game_won == null)
					game_won = new ThirdPlanetWin();

				setScreen(game_won);
				break;
			default:
				new_game_state = GAME_MENU;	
			}
		}
		
		super.render();
		//fps.log();
	}

	@Override
	public void dispose() {
		if (game_menu != null)
			game_menu.dispose();
		if (game_play != null)
			game_play.dispose();
		Assets.dispose();
	}
}
