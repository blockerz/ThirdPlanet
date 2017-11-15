package com.lofisoftware.thirdplanet.entities;

import java.util.ArrayList;
import java.util.List;

import com.lofisoftware.thirdplanet.universe.Point;
import com.lofisoftware.thirdplanet.universe.Sector;

public class LargeEntity extends Entity {

	private List<Point> points;
	
	public LargeEntity(Sector sector, Point p, int width, int height) {
		super(sector, p);
		init (width, height);
	}

	public LargeEntity(Sector sector, int width, int height) {
		super(sector);
		init (width, height);
	}

	private void init(int width2, int height2) {
		setWidth(width2);
		setHeight(height2);		
		setDestructible(false);
		setAttackPower(100);
		setMaxHitPoints(1000);
		setHitPoints(1000);
		setMaxSpeed(1);
		setName("Base Ship");
		setSpeed(0);
		setVisionRadius(20);
	}

	@Override
	public void setSectorPosition(int x, int y) {
		super.setSectorPosition(x, y);
		setAllPoints();
	}
	
	private void setAllPoints() {
	    points = new ArrayList<Point>();
	   
	    for (int x = getSectorPosition().x(); x < getSectorPosition().x()+getWidth(); x++){
	        for (int y = getSectorPosition().y(); y < getSectorPosition().y()+getHeight(); y++){
	            points.add(new Point(x, y));
	        }
	    }	
	}
	
	public boolean containsPoint(Point p) {
		
		for (Point point : points) {
			if (p.x() == point.x() && p.y() == point.y())
				return true;			
		}
		
		return false;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
	}

	@Override
	public boolean canSee(int wx, int wy) {
		// TODO Auto-generated method stub
		return super.canSee(wx, wy);
	}

	@Override
	public boolean canEnter(int x, int y) {
		// TODO Auto-generated method stub
		return super.canEnter(x, y);
	}
	
}
