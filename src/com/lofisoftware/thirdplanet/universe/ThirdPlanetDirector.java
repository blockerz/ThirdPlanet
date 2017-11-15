package com.lofisoftware.thirdplanet.universe;

import com.badlogic.gdx.math.MathUtils;
import com.lofisoftware.thirdplanet.ThirdPlanet;
import com.lofisoftware.thirdplanet.entities.AlienShip;
import com.lofisoftware.thirdplanet.messages.EntityDestroyed;
import com.lofisoftware.thirdplanet.messages.Handler;
import com.lofisoftware.thirdplanet.messages.Message;
import com.lofisoftware.thirdplanet.messages.MessageBus;
import com.lofisoftware.thirdplanet.messages.NextTurn;
import com.lofisoftware.thirdplanet.messages.Note;
import com.lofisoftware.thirdplanet.screens.TileFactory;

public class ThirdPlanetDirector implements Handler {

	public static enum GAME_STATE {
		STARTING, RUNNING, NEXTLEVEL, TRANSITION, LOST, WON, PAUSED
	};

	public static enum OBJECTIVES {
		STATION, WARP, ESCAPE, FINALBATTLE
	};

	private static GAME_STATE game_state;
	private static GAME_STATE new_game_state;
	private static OBJECTIVES currentObjective;

	private Universe universe;
	private static int game_level;
	private static int final_level;
	private Point startingPoint;
	private int startingQuad;
	UniverseFactory.SECTOR_TYPE sector_type;
	private boolean mothershipArrived;
	private boolean nextTurn;
	private int aliens;
	private int difficulty_factor;
	private float challenge_level;	
	private boolean beaconVisible;
	private static int transition;
	private Point mothershipLocation;

	public ThirdPlanetDirector() {
		MessageBus.subscribe(this);
		universe = new Universe();
		game_state = new_game_state = GAME_STATE.STARTING;
		game_level = 1;
		final_level = 10;
		difficulty_factor = 1;
		challenge_level = 0;
		beaconVisible = false;
		aliens = 0;
		transition = 0;
	}

	public void update() {

		if (game_state != new_game_state)
			game_state = new_game_state;

		switch (game_state) {

		case STARTING:
			if (game_level == final_level)
				setSectorObjective(OBJECTIVES.FINALBATTLE);
			else
				setSectorObjective(OBJECTIVES.STATION);
			setGameState(GAME_STATE.RUNNING);
			break;
		case RUNNING:
			if (nextTurn) {
				createNewEvents();
				nextTurn = false;
				challenge_level = (float) .01 * (difficulty_factor * game_level * universe.getPlayer().getTurns());
				helpMessages();
				//Gdx.app.log(ThirdPlanet.LOG, "Challenge Level: " + challenge_level);
			}
				
			universe.update();
			
			if (universe.getPlayer() != null && !universe.getPlayer().isAlive() && game_state != GAME_STATE.LOST){
				transition = 0;
				setGameState(GAME_STATE.LOST);
			}
			
			if (currentObjective == OBJECTIVES.ESCAPE) {
				universe.getActiveSector().remove(universe.getActiveSector().getPlayer());
				universe.getActiveSector().getBaseShip().setSpeed(20);
				universe.getActiveSector().getBaseShip().setRotation(Direction.WEST);
				
				transition = 0;
				setGameState(GAME_STATE.TRANSITION);
			}
			break;
		case LOST:
			if (transition > 120) {
				setGameState(GAME_STATE.PAUSED);
				ThirdPlanet.changeGameState(ThirdPlanet.GAME_LOST);
			}
			transition++;
			break;
		case WON:
			if (transition == 0){
				universe.getActiveSector().remove(universe.getActiveSector().getPlayer());
				universe.getActiveSector().remove(universe.getActiveSector().getMotherShip());
			}
			if (transition > 120) {
				setGameState(GAME_STATE.PAUSED);
				ThirdPlanet.changeGameState(ThirdPlanet.GAME_WON);
			}
			drawExplosion();
			transition++;
			break;
		case NEXTLEVEL:
			game_level++;
			universe.resetUniverse();
			challenge_level = 0;
			aliens = 0;
			beaconVisible = false;
			if (game_level == final_level)
				setSectorObjective(OBJECTIVES.FINALBATTLE);
			else
				setSectorObjective(OBJECTIVES.STATION);
			setGameState(GAME_STATE.RUNNING);
			break;
		case PAUSED:
			break;
		case TRANSITION:
			if (universe.getActiveSector().getBaseShip().getSectorPosition().x() > 0)
				universe.getActiveSector().getBaseShip().moveDirection(Direction.WEST);
			if (transition > 120)
				setGameState(GAME_STATE.NEXTLEVEL);
			transition++;
			break;
		default:
			break;
		}
	}

	public void drawExplosion() {
		if (transition % 10 == 0)
		for (int x = mothershipLocation.x(); x < mothershipLocation.x()+12;x++)
			for (int y = mothershipLocation.y(); y < mothershipLocation.y()+12;y++) {
				universe.getActiveSector().setTexture(x, y, TileFactory.getTileTexture(Tile.EXPLODE));
			}
	}

	private void helpMessages() {

		if(!beaconVisible && currentObjective == OBJECTIVES.STATION && universe.getPlayer().canSee(universe.getActiveSector().getBeacon())){
			beaconVisible = true;
			MessageBus.publish(new Note(null,"There is the beacon! Dock and get the next warp point."));
		}
		
	}

	private void setSectorObjective(OBJECTIVES obj) {

		currentObjective = obj;

		switch (currentObjective) {

		case STATION:
			resetLevel();
			createNewSector();
			Point p = getRandomPointInQuadrant(Direction.opposite(startingQuad));
			while(p.distanceTo(startingPoint) < 35)
				p = getRandomPointInQuadrant(Direction.opposite(startingQuad));
			UniverseFactory.createSpaceStation(universe.getActiveSector(), p);
			MessageBus.publish(new Note(null,
					"Find the beacon in the " + Message.getQuadrant(Direction.opposite(startingQuad)) + " quadrant."));
			break;
		case WARP:
		case FINALBATTLE:
			resetLevel();			
			createFinalSector();
			MessageBus.publish(new Note(null,
					"We found a weakness in the mothership. We believe that you can overload the reactor in your shuttle and pilot it into their core."));
			MessageBus.publish(new Note(null,
					"After all that you have done, now we ask of you the ultimate sacrifice. Once again you can save us all."));
			break;
		case ESCAPE:
			break;
		default:
			break;
		}

	}
	
	public void resetLevel() {
		mothershipArrived = false;
		nextTurn = false;
	}
	
	public void createNewSector() {

		startingQuad = Direction.randomDirection();
		startingPoint = getRandomPointInQuadrant(startingQuad);

		sector_type = MathUtils.randomBoolean() ? UniverseFactory.SECTOR_TYPE.SPACE
				: UniverseFactory.SECTOR_TYPE.NEBULA;
		universe.createActiveSector(sector_type);

		addBaseShip();

		// increase asteroids in space only sectors
		int factor = 0; 
		if (sector_type == UniverseFactory.SECTOR_TYPE.SPACE)
			factor = 10*difficulty_factor;
			
		UniverseFactory.createLargeAsteroidBelt(universe.getActiveSector(),
				MathUtils.random(factor, Sector.SECTOR_TILE_SIZE),
				Direction.randomDirection(),
				MathUtils.random(factor, Sector.SECTOR_TILE_SIZE));
		UniverseFactory.createAsteroidBelt(universe.getActiveSector(),
				MathUtils.random(factor*2, Sector.SECTOR_TILE_SIZE * 2),
				Direction.randomDirection(),
				MathUtils.random(factor*2, Sector.SECTOR_TILE_SIZE));

		// make sure the area around the player is clear
		clearArea(startingPoint);

	}
	
	public void addBaseShip() {
	
		//universe.setPlayer(UniverseFactory.createPlayer(universe.getActiveSector(), startingPoint));
		UniverseFactory.createPlayer(universe.getActiveSector(), startingPoint);
		Point baseShipLocation;

		if (startingQuad == Direction.WEST || startingQuad == Direction.NORTH) {
			baseShipLocation = startingPoint.plus(-6, 2);
			universe.getPlayer().setRotation(Direction.SOUTH);			
		} else
			baseShipLocation = startingPoint.plus(-6, -9);

		UniverseFactory.createBaseShip(universe.getActiveSector(),
				baseShipLocation);
		
		if (startingQuad == Direction.WEST || startingQuad == Direction.NORTH) {
			universe.getActiveSector().getBaseShip().setRotation(Direction.SOUTH);			
		} else
			universe.getActiveSector().getBaseShip().setRotation(Direction.NORTH);
		
	}
	
	public void createFinalSector() {

		startingQuad = Direction.randomDirection();		
		
		switch (startingQuad) {
		case Direction.NORTH:
			startingPoint = new Point (Sector.SECTOR_TILE_SIZE/2 + 16, Sector.SECTOR_TILE_SIZE/2 + 16);
			mothershipLocation = new Point (Sector.SECTOR_TILE_SIZE/2 - 16, Sector.SECTOR_TILE_SIZE/2 - 16);
			break;
		case Direction.EAST:
			startingPoint = new Point (Sector.SECTOR_TILE_SIZE/2 + 16, Sector.SECTOR_TILE_SIZE/2 - 16);
			mothershipLocation = new Point (Sector.SECTOR_TILE_SIZE/2 - 16, Sector.SECTOR_TILE_SIZE/2 + 16);
			break;
		case Direction.SOUTH:
			startingPoint = new Point (Sector.SECTOR_TILE_SIZE/2 - 16, Sector.SECTOR_TILE_SIZE/2 - 16);
			mothershipLocation = new Point (Sector.SECTOR_TILE_SIZE/2 + 16, Sector.SECTOR_TILE_SIZE/2 + 16);
			break;
		case Direction.WEST:
			startingPoint = new Point (Sector.SECTOR_TILE_SIZE/2 - 16, Sector.SECTOR_TILE_SIZE/2 + 16);
			mothershipLocation = new Point (Sector.SECTOR_TILE_SIZE/2 + 16, Sector.SECTOR_TILE_SIZE/2 - 16);
			break;
		default:
			mothershipLocation = new Point (Sector.SECTOR_TILE_SIZE/2, Sector.SECTOR_TILE_SIZE/2);			
		}

		universe.createActiveSector(UniverseFactory.SECTOR_TYPE.FINAL);

		addBaseShip();
		
		UniverseFactory.createDamagedAlienMotherShip(universe.getActiveSector(), mothershipLocation).setRotation(Direction.WEST);;		
		
		mothershipArrived = true;
		
		UniverseFactory.addAlienShips(universe.getActiveSector(), Sector.SECTOR_TILE_SIZE/4);
		aliens += Sector.SECTOR_TILE_SIZE/4;
		
		AlienShip alien;
		for (int a = -5; a < 10; a++) {
			alien = UniverseFactory.createAlienShip(universe.getActiveSector());
			alien.setRotation(Direction.EAST);
			alien.setSpeed(0);
			Point p = new Point (mothershipLocation.x()+12, mothershipLocation.y()+2+a);
			universe.getActiveSector().add(alien,p);
			aliens++;
		}

		// make sure the area around the player is clear
		clearArea(startingPoint);

	}


	public void createNewEvents() {

		if (!mothershipArrived && MathUtils.randomBoolean(challenge_level)){
			
			int mothershipDirection;
			
			if (startingQuad == Direction.WEST || startingQuad == Direction.NORTH) {
				mothershipLocation = startingPoint.plus(-6, -15);
				mothershipDirection = Direction.NORTH;
			} else {
				mothershipLocation = startingPoint.plus(-6, 3);
				mothershipDirection = Direction.SOUTH;
			}
			
			Point distance = universe.getPlayer().getSectorPosition().minus(mothershipLocation);
			
			// dont spawn on player
			if (!(distance.x() >= 0 && distance.x() <= 12 && distance.y() >= 0 && distance.y() <= 12)) {
			
				UniverseFactory.createAlienMotherShip(universe.getActiveSector(), mothershipLocation);
				universe.getActiveSector().getMotherShip().setRotation(mothershipDirection);
				
				mothershipArrived = true;			
				MessageBus.publish(new Note(null, "The Mother Ship has found us! Hurry!"));
				
				UniverseFactory.addAlienShips(universe.getActiveSector(), Sector.SECTOR_TILE_SIZE/10);
				aliens += Sector.SECTOR_TILE_SIZE/10;
			}
						
		}
		
		if (mothershipArrived && aliens < Sector.SECTOR_TILE_SIZE/6 * challenge_level && MathUtils.randomBoolean(0.3f)){
			UniverseFactory.addAlienShips(universe.getActiveSector(), Sector.SECTOR_TILE_SIZE/10);
			aliens += Sector.SECTOR_TILE_SIZE/10;
			MessageBus.publish(new Note(null, "Ships are pouring out of that thing. Watch yourself."));
		}
		
		if (mothershipArrived && game_level!=final_level) {
			universe.getActiveSector().getMotherShip().fireLaser(universe.getActiveSector().getMotherShip().getRotation());
			universe.getActiveSector().getBaseShip().fireLaser(universe.getActiveSector().getBaseShip().getRotation());
		}
		
		if (MathUtils.randomBoolean(0.1f)) {
			UniverseFactory.addComet(universe.getActiveSector(), getRandomPointInQuadrant(Direction.randomDirection()), Direction.randomDirection());
		}

	}

	private void clearArea(Point startingPoint) {

		for (Point neighbor : startingPoint.neighbors()) {
			universe.getActiveSector().remove(
					universe.getActiveSector().getEntity(neighbor));
		}
	}

	public Point getRandomPointInQuadrant(int direction) {

		Point starting;

		// NORTH = NE
		// EAST = SE
		// SOUTH = SW
		// WEST = NW

		switch (direction) {
		case Direction.NORTH:
			starting = new Point(MathUtils.random(Sector.SECTOR_TILE_SIZE / 2,
					Sector.SECTOR_TILE_SIZE), MathUtils.random(
					Sector.SECTOR_TILE_SIZE / 2, Sector.SECTOR_TILE_SIZE));
			break;
		case Direction.EAST:
			starting = new Point(MathUtils.random(Sector.SECTOR_TILE_SIZE / 2,
					Sector.SECTOR_TILE_SIZE), MathUtils.random(0,
					Sector.SECTOR_TILE_SIZE / 2));
			break;
		case Direction.SOUTH:
			starting = new Point(MathUtils.random(0,
					Sector.SECTOR_TILE_SIZE / 2), MathUtils.random(0,
					Sector.SECTOR_TILE_SIZE / 2));
			break;
		case Direction.WEST:
			starting = new Point(MathUtils.random(0,
					Sector.SECTOR_TILE_SIZE / 2), MathUtils.random(
					Sector.SECTOR_TILE_SIZE / 2, Sector.SECTOR_TILE_SIZE));
			break;
		default:
			starting = new Point(Sector.SECTOR_TILE_SIZE / 2,
					Sector.SECTOR_TILE_SIZE / 2);
		}

		// stay out of corners
		int dx = 0;
		int dy = 0;

		if (starting.x() < 15)
			dx += 15;
		else if (Sector.SECTOR_TILE_SIZE - starting.x() < 15)
			dx -= 15;

		if (starting.y() < 15)
			dy += 15;
		else if (Sector.SECTOR_TILE_SIZE - starting.y() < 15)
			dy -= 15;

		return starting.plus(dx, dy);
	}


	public static GAME_STATE getGameState() {
		return game_state;
	}

	public static void setGameState(GAME_STATE game_state) {
		ThirdPlanetDirector.new_game_state = game_state;
	}

	public static OBJECTIVES getCurrentObjective() {
		return currentObjective;
	}

	public static void setCurrentObjective(OBJECTIVES obj) {
		currentObjective = obj;
	}

	public Universe getUniverse() {
		return universe;
	}

	public static void markComplete(OBJECTIVES obj) {

		if (currentObjective == obj) {

			switch (currentObjective) {

			case STATION:
				if (game_level == final_level)
					MessageBus.publish(new Note(null,
							"This is it! This is the beacon that leads us home. We are so close now and we owe it all to you."));
				else
					MessageBus.publish(new Note(null,
							"You got the next warp location! Get back to Destiny."));
				setCurrentObjective(OBJECTIVES.WARP);
				break;
			case WARP:
				if (game_level == final_level)
					MessageBus.publish(new Note(null,
							"Let's go home!"));
				else
					MessageBus.publish(new Note(null,
							"Great job! Let's get out of here."));

				setCurrentObjective(OBJECTIVES.ESCAPE);				
				break;
			case FINALBATTLE:
				MessageBus.publish(new Note(null,
						"It is done. We will miss you."));
				transition = 0;
				setGameState(GAME_STATE.WON);
				break;
			case ESCAPE:
				break;
			default:
				break;
			}

		}
	}

	@Override
	public void handle(Message message) {
		if (NextTurn.class.isAssignableFrom(message.getClass())){
			nextTurn = true;
		}
		if (EntityDestroyed.class.isAssignableFrom(message.getClass())){
			EntityDestroyed m = (EntityDestroyed) message;
			if (m.destroyed.getName().equalsIgnoreCase("Alien Ship"))
				aliens--;
		}
		
	}

}
