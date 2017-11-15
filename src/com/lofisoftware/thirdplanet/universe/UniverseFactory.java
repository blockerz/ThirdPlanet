package com.lofisoftware.thirdplanet.universe;

import com.lofisoftware.thirdplanet.Assets;
import com.lofisoftware.thirdplanet.entities.AlienShip;
import com.lofisoftware.thirdplanet.entities.AlienShipAI;
import com.lofisoftware.thirdplanet.entities.Asteroid;
import com.lofisoftware.thirdplanet.entities.BaseShip;
import com.lofisoftware.thirdplanet.entities.BaseShipAI;
import com.lofisoftware.thirdplanet.entities.Explosion;
import com.lofisoftware.thirdplanet.entities.LargeAsteroid;
import com.lofisoftware.thirdplanet.entities.LargeEntity;
import com.lofisoftware.thirdplanet.entities.Player;
import com.lofisoftware.thirdplanet.entities.PlayerAI;
import com.lofisoftware.thirdplanet.entities.Projectile;
import com.lofisoftware.thirdplanet.entities.ProjectileAI;
import com.lofisoftware.thirdplanet.entities.SpaceStation;
import com.lofisoftware.thirdplanet.screens.TileFactory;

public class UniverseFactory {

	public static enum SECTOR_TYPE {SPACE,NEBULA, FINAL};
	
	public static Sector buildNebulaSector() {
		Sector s = new SectorBuilder(Sector.SECTOR_TILE_SIZE,
				Sector.SECTOR_TILE_SIZE).doCellularAutomata(Tile.SPACE,
				Tile.NEBULA, 10).build();

		for (int x = 0; x < s.getWidth(); x++)
			for (int y = 0; y < s.getHeight(); y++)
				s.setTexture(x, y, TileFactory.getTileTexture(s.getTile(x, y)));

		return s;
	}

	public static Sector buildSpaceSector() {
		Sector s = new SectorBuilder(Sector.SECTOR_TILE_SIZE,
				Sector.SECTOR_TILE_SIZE).fillTile(Tile.SPACE).build();

		for (int x = 0; x < s.getWidth(); x++)
			for (int y = 0; y < s.getHeight(); y++)
				s.setTexture(x, y, TileFactory.getTileTexture(s.getTile(x, y)));

		return s;
	}
	
	public static Sector buildFinalSector() {
		Sector s = new SectorBuilder(Sector.SECTOR_TILE_SIZE,
				Sector.SECTOR_TILE_SIZE).fillTile(Tile.SPACE).build();

		for (int x = 0; x < s.getWidth(); x++)
			for (int y = 0; y < s.getHeight(); y++)
				s.setTexture(x, y, TileFactory.getTileTexture(s.getTile(x, y)));
		
		for (int x = (Sector.SECTOR_TILE_SIZE/2 - 40/2); x < (Sector.SECTOR_TILE_SIZE/2 + 40/2); x++)
			for (int y = (Sector.SECTOR_TILE_SIZE/2 - 40/2); y < (Sector.SECTOR_TILE_SIZE/2 + 40/2); y++)
				s.setTexture(x, y, Assets.getTexture(Assets.THIRDPLANET,x-(Sector.SECTOR_TILE_SIZE/2 - 40/2),y-(Sector.SECTOR_TILE_SIZE/2 - 40/2)));

		return s;
	}

	public static BaseShip createBaseShip(Sector sector, Point p) {
		BaseShip ship = new BaseShip(sector, 12, 8);
		ship.setName("Destiny");
		ship.setTile(Tile.BASESHIP);
		ship.setRotation(Direction.WEST);
		ship.setSectorPosition(p.x(), p.y());
		new BaseShipAI(ship);
		sector.add(ship, p);
		sector.setBaseShip(ship);
		return ship;
	}
	
	public static SpaceStation createSpaceStation(Sector sector, Point p) {
		SpaceStation beacon = new SpaceStation(sector, 3, 3);
		beacon.setName("Beacon");
		beacon.setTile(Tile.SPACESTATION);
		beacon.setRotation(Direction.NORTH);
		beacon.setSectorPosition(p.x(), p.y());
		new BaseShipAI(beacon);
		sector.add(beacon, p);
		sector.setBeacon(beacon);
		return beacon;
	}

	public static LargeEntity createThirdPlanet(Sector sector, Point p) {
		LargeEntity planet = new LargeEntity(sector, 40, 40);
		planet.setName("Third Planet");
		planet.setTile(Tile.THIRDPLANET);
		planet.setRotation(Direction.NORTH);
		planet.setSectorPosition(p.x(), p.y());
		new BaseShipAI(planet);
		sector.add(planet, p);
		return planet;
	}

	public static BaseShip createDamagedAlienMotherShip(Sector sector, Point p) {
		BaseShip ship = new BaseShip(sector, 12, 12);
		ship.setTile(Tile.ALIENBASESHIPD);
		ship.setRotation(Direction.WEST);
		ship.setSectorPosition(p.x(), p.y());
		ship.setName("Mother Ship");
		new BaseShipAI(ship);
		sector.add(ship, p);
		sector.setMotherShip(ship);
		return ship;
	}
	
	public static BaseShip createAlienMotherShip(Sector sector, Point p) {
		BaseShip ship = new BaseShip(sector, 12, 12);
		ship.setTile(Tile.ALIENBASESHIP);
		ship.setRotation(Direction.WEST);
		ship.setSectorPosition(p.x(), p.y());
		ship.setName("Mother Ship");
		new BaseShipAI(ship);
		sector.add(ship, p);
		sector.setMotherShip(ship);
		return ship;
	}

	public static Player createPlayer(Sector sector, Point p) {
		Player player = new Player(sector);		
		player.setRotation(Direction.NORTH);
		// player.setSectorPosition(0,0);
		new PlayerAI(player);
		sector.addPlayer(player, p);
		return player;
	}


	public static LargeAsteroid addComet(Sector sector, Point p, int direction) {
		LargeAsteroid asteroid = new LargeAsteroid(sector, 3, 3);
		switch (direction) {
		case Direction.NORTH:
			asteroid.setTile(Tile.COMET24_NORTH);
			break;
		case Direction.SOUTH:
			asteroid.setTile(Tile.COMET24_SOUTH);
			break;
		case Direction.EAST:
			asteroid.setTile(Tile.COMET24_EAST);
		case Direction.WEST:
			asteroid.setTile(Tile.COMET24_WEST);
			break;
		}
		asteroid.setRotation(direction);
		asteroid.setSpeed(2);
		asteroid.setName("Comet");
		new ProjectileAI(asteroid);
		sector.add(asteroid, p);
		return asteroid;
	}

	public static Explosion createExplosion(Sector sector, Point p) {
		Explosion explosion = new Explosion(sector, p);
		explosion.setTile(Tile.EXPLOSION);
		sector.add(explosion, p);
		return explosion;
	}

	public static AlienShip createAlienShip(Sector sector) {
		AlienShip ship = new AlienShip(sector);
		ship.setRotation(Direction.randomDirection());
		ship.setSpeed(1);
		new AlienShipAI(ship);
		return ship;
	}

	public static Projectile createLaser(Sector sector, Point p, int direction, String firedBy) {
		Projectile laser = new Projectile(sector, p);
		laser.setName(firedBy);
		switch (direction) {
		case Direction.NORTH:
		case Direction.SOUTH:
			laser.setTile(Tile.LASER_NORTH_SOUTH);
			break;
		case Direction.EAST:
		case Direction.WEST:
			laser.setTile(Tile.LASER_EAST_WEST);
			break;
		default:

		}		
		laser.setRotation(direction);
		laser.setSpeed(Sector.SECTOR_TILE_SIZE);
		new ProjectileAI(laser);
		sector.add(laser, p);
		return laser;
	}

	public static void addAlienShips(Sector sector, int density) {
		AlienShip ship;
		for (int a = 0; a < density; a++) {
			ship = createAlienShip(sector);
			sector.add(ship);
		}
	}

	public static Asteroid createAsteroid(Sector sector) {
		Asteroid asteroid = new Asteroid(sector);
		asteroid.setTile(Tile.ASTEROID8);
		asteroid.setSpeed(1);
		new ProjectileAI(asteroid);
		return asteroid;
	}

	public static LargeAsteroid createLargeAsteroid(Sector sector) {
		LargeAsteroid asteroid = new LargeAsteroid(sector, 2, 2);
		asteroid.setTile(Tile.ASTEROID16);
		asteroid.setSpeed(1);
		new ProjectileAI(asteroid);
		return asteroid;
	}
	
	public static void createAsteroidBelt(Sector sector, int density,
			int direction, int size) {
		Asteroid asteroid;
		
		int x = Sector.SECTOR_TILE_SIZE;
		int y = Sector.SECTOR_TILE_SIZE;
		
		switch (direction) {
		case Direction.NORTH:
		case Direction.SOUTH:
			x = size;
			break;
		case Direction.EAST:
		case Direction.WEST:
			y = size;
			break;
		default:

		}
		
		for (int a = 0; a < density; a++) {
			asteroid = createAsteroid(sector);
			asteroid.setRotation(direction);
			asteroid.setSpeed(1);
			sector.add(asteroid,x,y);
		}
	}

	public static void createLargeAsteroidBelt(Sector sector, int density,
			int direction, int size) {
		LargeAsteroid asteroid;
		
		int x = Sector.SECTOR_TILE_SIZE;
		int y = Sector.SECTOR_TILE_SIZE;
		
		switch (direction) {
		case Direction.NORTH:
		case Direction.SOUTH:
			x = size;
			break;
		case Direction.EAST:
		case Direction.WEST:
			y = size;
			break;
		default:

		}
		
		for (int a = 0; a < density; a++) {
			asteroid = createLargeAsteroid(sector);
			asteroid.setRotation(direction);
			asteroid.setSpeed(1);
			sector.add(asteroid,x,y);
		}
	}
}
