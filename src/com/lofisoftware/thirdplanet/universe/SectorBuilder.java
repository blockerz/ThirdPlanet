package com.lofisoftware.thirdplanet.universe;

public class SectorBuilder {

	private int width, height;
	private Tile[][] map;

	public SectorBuilder(int width, int height) {
		this.width = width;
		this.height = height;
		this.map = new Tile[width][height];
	}

	public Sector build() {
		return new Sector(map);
	}

	public SectorBuilder doCellularAutomata(Tile tile1, Tile tile2, int times) {
		return randomizeTiles(tile1, tile2).smooth(tile1, tile2, times);
	}

	public SectorBuilder fillTile(Tile tile) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				map[x][y] = tile;
			}
		}
		return this;
	}
	
	private SectorBuilder randomizeTiles(Tile tile1, Tile tile2) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				map[x][y] = Math.random() < 0.5 ? tile1 : tile2;
			}
		}
		return this;
	}

	private SectorBuilder smooth(Tile tile1, Tile tile2, int times) {
		Tile[][] tiles2 = new Tile[width][height];
		for (int time = 0; time < times; time++) {

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					int tileCnt1 = 0;
					int tileCnt2 = 0;

					for (int ox = -1; ox < 2; ox++) {
						for (int oy = -1; oy < 2; oy++) {
							if (x + ox < 0 || x + ox >= width || y + oy < 0
									|| y + oy >= height)
								continue;

							if (map[x + ox][y + oy] == tile1)
								tileCnt1++;
							else
								tileCnt2++;
						}
					}
					tiles2[x][y] = tileCnt1 >= tileCnt2 ? tile1 : tile2;
				}
			}
			map = tiles2;
		}
		return this;
	}

}
