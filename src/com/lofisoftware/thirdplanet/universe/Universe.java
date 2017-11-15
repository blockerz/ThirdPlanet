package com.lofisoftware.thirdplanet.universe;

import java.util.ArrayList;
import java.util.List;

import com.lofisoftware.thirdplanet.entities.Player;

public class Universe {
	
	private List<Sector> sectors;
	private Sector activeSector;
	//private Player player;

	public Universe(){
		this.sectors = new ArrayList<Sector>();
	}
	
	public void resetUniverse() {
		sectors.clear();
		activeSector = null;
	}
	
	public Sector createActiveSector(UniverseFactory.SECTOR_TYPE type) {
		
		switch (type) {
		case SPACE:
			activeSector = UniverseFactory.buildSpaceSector();
			break;
		case NEBULA:
			activeSector = UniverseFactory.buildNebulaSector();
			break;
		case FINAL:
			activeSector = UniverseFactory.buildFinalSector();
			break;
		}
		sectors.add(activeSector);
		return activeSector;
	}
	
	public Sector getActiveSector() {
		return activeSector;
	}

	public Player getPlayer() {
		return activeSector.getPlayer();
	}
	
	public void update(){

    	for (Sector s : sectors)
    			s.update();
    	
	}
}
