package com.lofisoftware.thirdplanet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lofisoftware.thirdplanet.screens.TileFactory;

public class Assets {
	
	public final static int ASTEROIDS8 = 1;
	public final static int ASTEROIDS16 = 2;
	public final static int SHIPS8 = 3;
	public final static int ALIENSHIPS8 = 4;
	public final static int ITEMS8 = 5;
	public final static int BASESHIP = 6;
	public final static int ALIENBASESHIP = 7;
	public final static int COMETS24 = 8;
	public final static int SPACESTATION = 9;
	public final static int THIRDPLANET = 10;
	public final static int ALIENBASESHIPD = 11;
	
	private static TextureAtlas gameatlas;
	private static Skin gameskin;
	private static Skin uiskin;
	private static BitmapFont fontSmall;
	private static BitmapFont fontLarge;

	private static TextureRegion [][] asteroids16;
	private static TextureRegion [][] asteroids8;
	private static TextureRegion [][] ships8;
	private static TextureRegion [][] alienships8;
	private static TextureRegion [][] items8;
	private static TextureRegion [][] baseship;
	private static TextureRegion [][] mothership;
	private static TextureRegion [][] mothershipd;
	private static TextureRegion [][] comets;
	private static TextureRegion [][] spacestation;
	private static TextureRegion [][] thirdplanet;

	public static void load() {
		gameatlas = new TextureAtlas(Gdx.files.internal("data/ThirdPlanet.pack"));
		uiskin = new Skin(Gdx.files.internal("data/uiskin.json"));
		gameskin = new Skin();
		gameskin.addRegions(gameatlas);
		//font = new BitmapFont(Gdx.files.internal("data/default.fnt"));
		         //textureAtlas.findRegion("calibri"), false);
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/Akashi.ttf"));
		fontSmall = generator.generateFont(16);
		fontLarge = generator.generateFont(30);
		
		//sample = atlas.findRegion("sample");
		asteroids16 = gameskin.getRegion("scifi-asteroids-16").split(16,16);
		asteroids8 = gameskin.getRegion("scifi-asteroids-8").split(8,8);
		ships8 = gameskin.getRegion("scifi-ships-8").split(8,8);
		alienships8 = gameskin.getRegion("scifi-alien-ships-8").split(8,8);
		items8 = gameskin.getRegion("scifi-items-8").split(8,8);
		baseship = gameskin.getRegion("scifi-baseship-96-48").split(96,48);
		mothership = gameskin.getRegion("scifi-mothershipship-96").split(96,96);
		mothershipd = gameskin.getRegion("scifi-mothershipship-damaged-96").split(96,96);
		comets = gameskin.getRegion("scifi-comets-24").split(24,24);
		spacestation = gameskin.getRegion("scifi-beacon-24").split(24,24);
		thirdplanet = gameskin.getRegion("scifi-thirdplanet-320").split(8,8);
		
	}
	
	public static TextureAtlas getGameatlas() {
		return gameatlas;
	}

	public static Skin getGameskin() {
		return gameskin;
	}

	public static Skin getUiskin() {
		return uiskin;
	}

	public static BitmapFont getSmallFont() {
		return fontSmall;
	}
	
	public static BitmapFont getLargeFont() {
		return fontLarge;
	}
	
	public static TextureRegion getTexture(int regionNumber, int x, int y) {
		
		TextureRegion [][] region = getTextureRegion(regionNumber);
		
		if (region != null && x < region.length && y < region[0].length)
			return region[x][y];
		
		return TileFactory.createEmptyTile();
	}
	
	public static TextureRegion[][] getTextureRegion(int regionNumber) {
		TextureRegion [][] region;
		
		switch(regionNumber){
		case (ASTEROIDS16):
			region = asteroids16;
			break;
		case (ASTEROIDS8):
			region = asteroids8;
			break;
		case (SHIPS8):
			region = ships8;
			break;
		case (ALIENSHIPS8):
			region = alienships8;
			break;
		case (ITEMS8):
			region = items8;
			break;
		case (BASESHIP):
			region = baseship;
			break;
		case (ALIENBASESHIP):
			region = mothership;
			break;
		case (ALIENBASESHIPD):
			region = mothershipd;
			break;
		case (COMETS24):
			region = comets;
			break;
		case (SPACESTATION):
			region = spacestation;
			break;
		case (THIRDPLANET):
			region = thirdplanet;
			break;
		default:
			region = null;
		}
		
		return region;
	}
	
	public static void dispose() {
		gameskin.dispose();
		uiskin.dispose();
		gameatlas.dispose();
	}
}
