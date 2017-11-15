package com.lofisoftware.thirdplanet.universe;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Point {
	private int x;
	private int y;

	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	public Point copy() {
		return new Point(x, y);
	}

	public int distanceTo(Point other){
		return Math.max(Math.abs(x-other.x), Math.abs(y-other.y)); 
	}

	public List<Point> neighbors() {
		List<Point> neighbors = Arrays.asList(
				new Point(x-1,y-1), new Point(x+0,y-1), new Point(x+1,y-1), 
				new Point(x-1,y+0),                     new Point(x+1,y+0),
				new Point(x-1,y+1), new Point(x+0,y+1), new Point(x+1,y+1));

		Collections.shuffle(neighbors);
		return neighbors;
	}

	public Point plus(int dx, int dy) {
		return new Point(x+dx, y+dy);
	}

	public Point plus(Point other){
		return new Point(x+other.x, y+other.y);
	}

	public Point minus(Point other){
		return new Point(x-other.x, y-other.y);
	}
}