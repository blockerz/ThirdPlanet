package com.lofisoftware.thirdplanet.util;

import java.util.List;

import com.lofisoftware.thirdplanet.entities.Entity;
import com.lofisoftware.thirdplanet.universe.Point;

public class Path1 {

	private static AStarPathFinder1 pf = new AStarPathFinder1();

	private List<Point> points;

	public List<Point> points() {
		return points;
	}

	public Path1(Entity entity, int x, int y) {
		points = pf.findPath(entity, new Point(entity.getSectorPosition().x(),
				entity.getSectorPosition().y()), new Point(x, y), 300);
	}
}