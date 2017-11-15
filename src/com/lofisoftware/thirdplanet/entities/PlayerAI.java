package com.lofisoftware.thirdplanet.entities;

import com.lofisoftware.thirdplanet.messages.EntityDestroyed;
import com.lofisoftware.thirdplanet.messages.Message;
import com.lofisoftware.thirdplanet.messages.MessageBus;
import com.lofisoftware.thirdplanet.messages.Note;
import com.lofisoftware.thirdplanet.universe.Point;
import com.lofisoftware.thirdplanet.universe.Sector;
import com.lofisoftware.thirdplanet.universe.ThirdPlanetDirector;

public class PlayerAI extends EntityAI {

	Player player;
	
	public PlayerAI(Player entity) {
		super(entity);
		player = entity;
	}

	public void onEnter(Point p, Sector sector) {
		if (sector.canEnter(p)) {				
			
			if (player.getMovesRemaining() == 0){
				player.setSectorPosition(p);
				player.setUpdated(true);
			}
			else if (player.getEnergy() > 0){
				player.setSectorPosition(p);
				player.addEnergy(-1);
				player.setUsedEnergy(true);
			}
			else player.setSpeed(1);
		} else if (sector.isValidPos(p)) {
			Entity e = sector.getEntity(p);

			if (sector.getTile(p).isSolid()) {
				MessageBus.publish(new EntityDestroyed(sector, player, player,
						"You crashed into "
								+ Message.aOrAn(sector.getTile(p).getName())));
				player.modifyHitPoints(entity, -1000);
			} else if (e != null) {
				boolean docked = false;
				if (e.getName().equalsIgnoreCase("Destiny")
						|| e.getName().equalsIgnoreCase("Beacon")
						|| e.getName().equalsIgnoreCase("Mother Ship"))
					docked = dockShip(e);

				if (!docked) {
					MessageBus.publish(new EntityDestroyed(sector, player,
							player, "You crashed into "
									+ Message.aOrAn(sector.getEntity(p)
											.getName())));
					player.modifyHitPoints(player, -1000);
				}
			}

		}
	}

	public boolean dockShip(Entity e) {
		if (ThirdPlanetDirector.getCurrentObjective() == ThirdPlanetDirector.OBJECTIVES.STATION 
				&& e.getName().equalsIgnoreCase("Beacon"))
			ThirdPlanetDirector.markComplete(ThirdPlanetDirector.OBJECTIVES.STATION);
		else if (ThirdPlanetDirector.getCurrentObjective() == ThirdPlanetDirector.OBJECTIVES.WARP 
				&& e.getName().equalsIgnoreCase("Destiny"))
			ThirdPlanetDirector.markComplete(ThirdPlanetDirector.OBJECTIVES.WARP);
		else if (ThirdPlanetDirector.getCurrentObjective() == ThirdPlanetDirector.OBJECTIVES.FINALBATTLE 
				&& e.getName().equalsIgnoreCase("Mother Ship")) {
			
			if (player.getSectorPosition().x() == e.getSectorPosition().x()+12 
					&& (player.getSectorPosition().y() == e.getSectorPosition().y()+6
					|| player.getSectorPosition().y() == e.getSectorPosition().y()+5)) {
				ThirdPlanetDirector.markComplete(ThirdPlanetDirector.OBJECTIVES.FINALBATTLE);
			}
			else 
				return false;
				
		}
		else if (e.getName().equalsIgnoreCase("Mother Ship"))
			return false;
		else 	
			MessageBus.publish(new Note(entity, "It's not time to dock there."));
		player.setUpdated(true);
		return true;
	}
}
